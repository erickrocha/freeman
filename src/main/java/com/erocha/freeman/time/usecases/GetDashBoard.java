package com.erocha.freeman.time.usecases;

import com.erocha.freeman.commons.exceptions.ResourceNotFoundException;
import com.erocha.freeman.commons.utils.DateTools;
import com.erocha.freeman.security.domains.User;
import com.erocha.freeman.security.usecases.GetUser;
import com.erocha.freeman.time.domains.Appointment;
import com.erocha.freeman.time.gateways.AppointmentGateway;
import com.erocha.freeman.time.http.json.Dashboard;
import com.erocha.freeman.time.http.json.DayAmount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

@Service
@Slf4j
public class GetDashBoard {


    private AppointmentGateway gateway;

    private GetUser getUser;

    public GetDashBoard(AppointmentGateway gateway, GetUser getUser) {
        this.gateway = gateway;
        this.getUser = getUser;
    }

    public Dashboard execute(String username, LocalDate startDate, LocalDate endDate) {
        User me = getUser.execute(username).orElseThrow(ResourceNotFoundException::new);
        if (startDate == null) {
            LocalDate today = LocalDate.now();
            startDate = DateTools.findFirstDayOfWeek(today, DayOfWeek.SUNDAY);
            endDate = DateTools.findLastDayOfWeek(today, DayOfWeek.SATURDAY);
        }
        Stream<Appointment> appointmentStream = gateway.query(createQueryBYUserIdAndDateBetween(me.getId(), startDate, endDate));

        Dashboard dashboard = Dashboard.builder().month(startDate.getMonth()).year(startDate.getYear()).monthOfYear(startDate.getMonthValue())
                .startDate(DateTools.findFirstDayOfWeek(startDate, DayOfWeek.SUNDAY))
                .endDate(DateTools.findLastDayOfWeek(endDate != null ? endDate : LocalDate.now(), DayOfWeek.SATURDAY)).build();
        LocalDate firstDayOfWeek = DateTools.findFirstDayOfWeek(startDate, DayOfWeek.SUNDAY);
        LocalDate lastDayOfWeek = DateTools.findLastDayOfWeek(endDate != null ? endDate : LocalDate.now(), DayOfWeek.SATURDAY);
        List<Appointment> appointments = appointmentStream.toList();
        while (firstDayOfWeek.isBefore(lastDayOfWeek) || firstDayOfWeek.equals(lastDayOfWeek)) {
            dashboard.addDaysAmount(DayAmount.builder().date(firstDayOfWeek).appointments(getAppointmentsByDate(firstDayOfWeek, appointments)).build());
            firstDayOfWeek = firstDayOfWeek.plusDays(1);
        }
        return dashboard;
    }

    private Query createQueryBYUserIdAndDateBetween(String userId, LocalDate startDate, LocalDate endDate) {
        Query query = new Query();
        Criteria requiredCriteria = Criteria.where("userId").is(userId).and("date").lte(endDate).gte(startDate);
        query.addCriteria(requiredCriteria);
        return query;
    }

    private List<Appointment> getAppointmentsByDate(LocalDate date, List<Appointment> appointments) {
        return appointments.stream().filter(appointment -> appointment.getDate().equals(date)).toList();
    }
}
