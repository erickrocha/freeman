package com.erocha.freeman.management.usecases;

import com.erocha.freeman.commons.utils.DateTools;
import com.erocha.freeman.commons.utils.ImageBase64Handler;
import com.erocha.freeman.hr.gateways.PersonGateway;
import com.erocha.freeman.management.http.json.Entry;
import com.erocha.freeman.management.http.json.UserEntry;
import com.erocha.freeman.management.http.mapper.EntryMapper;
import com.erocha.freeman.time.domains.Appointment;
import com.erocha.freeman.time.domains.Status;
import com.erocha.freeman.time.gateways.AppointmentGateway;
import com.erocha.freeman.time.gateways.query.builder.AppointmentQueryBuilder;
import com.erocha.freeman.time.http.json.DayAmount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@Slf4j
public class ApproveEntries {

    private AppointmentGateway appointmentGateway;

    private PersonGateway personGateway;

    private EntryMapper entryMapper;

    public ApproveEntries(AppointmentGateway appointmentGateway, PersonGateway personGateway, EntryMapper entryMapper) {
        this.appointmentGateway = appointmentGateway;
        this.personGateway = personGateway;
        this.entryMapper = entryMapper;
    }

    public UserEntry execute(String userId, LocalDate startDate, LocalDate endDate, List<String> appointmentIds) {
        Stream<Appointment> appointments = appointmentGateway.query(createQuery(userId, appointmentIds));
        List<Appointment> approves = appointments.map(this::approve).toList();
        appointmentGateway.saveAll(approves);

        UserEntry.UserEntryBuilder entryBuilder = UserEntry.builder();
        personGateway.findById(userId).ifPresent(person -> {
            List<Appointment> appointmentList = appointmentGateway
                    .query(AppointmentQueryBuilder.queryByUsersDateBetween(List.of(person.getId()), startDate, endDate))
                    .toList();
            Optional.ofNullable(person.getAvatar()).ifPresent(avatar -> entryBuilder.avatar(ImageBase64Handler.fromByteArrayToString(avatar)));
            LocalDate firstDayOfWeek = DateTools.findFirstDayOfWeek(startDate != null ? startDate : LocalDate.now(), DayOfWeek.SUNDAY);
            LocalDate lastDayOfWeek = DateTools.findLastDayOfWeek(endDate != null ? endDate : LocalDate.now(), DayOfWeek.SATURDAY);
            entryBuilder.userId(person.getId()).name(person.getName()).build();
            while (firstDayOfWeek.isBefore(lastDayOfWeek) || firstDayOfWeek.equals(lastDayOfWeek)) {
                entryBuilder.day(DayAmount.builder().date(firstDayOfWeek).entries(getAppointmentsByDate(firstDayOfWeek, appointmentList)).build());
                firstDayOfWeek = firstDayOfWeek.plusDays(1);
            }
        });
        return entryBuilder.build();
    }

    private List<Entry> getAppointmentsByDate(LocalDate date, List<Appointment> appointments) {
        return appointments.stream().filter(appointment -> appointment.getDate().equals(date)).map(entryMapper::convertTransferObject)
                .toList();
    }

    private Appointment approve(Appointment appointment) {
        appointment.setStatus(Status.APPROVED);
        return appointment;
    }

    private Query createQuery(String userId, List<String> ids) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").in(ids));
        query.addCriteria(Criteria.where("userId").is(userId));
        return query;
    }
}
