package com.erocha.freeman.management.usecases;

import com.erocha.freeman.commons.exceptions.ResourceNotFoundException;
import com.erocha.freeman.commons.utils.DateTools;
import com.erocha.freeman.commons.utils.ImageBase64Handler;
import com.erocha.freeman.hr.gateways.PersonGateway;
import com.erocha.freeman.management.domains.Member;
import com.erocha.freeman.management.domains.Team;
import com.erocha.freeman.management.gateways.query.builder.TeamQueryBuilder;
import com.erocha.freeman.management.http.json.EntriesWrapper;
import com.erocha.freeman.management.http.json.Entry;
import com.erocha.freeman.management.http.json.UserEntry;
import com.erocha.freeman.management.http.mapper.EntryMapper;
import com.erocha.freeman.security.domains.User;
import com.erocha.freeman.security.usecases.GetUser;
import com.erocha.freeman.time.domains.Appointment;
import com.erocha.freeman.time.gateways.query.builder.AppointmentQueryBuilder;
import com.erocha.freeman.time.http.json.DayAmount;
import com.erocha.freeman.time.usecases.GetAppointment;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class GetEntries {


    private GetUser getUser;


    private GetTeam getTeam;


    private GetAppointment getAppointment;


    private EntryMapper entryMapper;


    private PersonGateway personGateway;


    public GetEntries(GetUser getUser, GetTeam getTeam, GetAppointment getAppointment, EntryMapper entryMapper, PersonGateway personGateway) {
        this.getUser = getUser;
        this.getTeam = getTeam;
        this.getAppointment = getAppointment;
        this.entryMapper = entryMapper;
        this.personGateway = personGateway;
    }

    public EntriesWrapper execute(String username, LocalDate startDate, LocalDate endDate) {
        EntriesWrapper.EntriesWrapperBuilder builder = EntriesWrapper.builder();
        builder.startDate(startDate);
        builder.endDate(endDate);
        User me = getUser.execute(username).orElseThrow(ResourceNotFoundException::new);

        List<Team> teams = getTeam.execute(TeamQueryBuilder.queryByManagerId(me.getId()));

        List<Member> members = teams.stream().flatMap(team -> team.getMembers().stream()).toList();
        List<String> membersId = members.stream().map(Member::getId).toList();

        List<Appointment> appointments = getAppointment.execute(AppointmentQueryBuilder.queryByUsersDateBetween(membersId, startDate, endDate)).toList();

        for (Member teamMember : members) {
            UserEntry.UserEntryBuilder entryBuilder = UserEntry.builder();
            entryBuilder.userId(teamMember.getId()).name(teamMember.getName());
            List<Appointment> appointmentsByUser = appointments.stream().filter(appointment -> appointment.getUserId().equals(teamMember.getId()))
                    .toList();
            personGateway.findById(teamMember.getId()).flatMap(person -> Optional.ofNullable(person.getAvatar())).ifPresent(avatar -> entryBuilder.avatar(ImageBase64Handler.fromByteArrayToString(avatar)));
            LocalDate firstDayOfWeek = DateTools.findFirstDayOfWeek(startDate != null ? startDate : LocalDate.now(), DayOfWeek.SUNDAY);
            LocalDate lastDayOfWeek = DateTools.findLastDayOfWeek(endDate != null ? endDate : LocalDate.now(), DayOfWeek.SATURDAY);
            while (firstDayOfWeek.isBefore(lastDayOfWeek) || firstDayOfWeek.equals(lastDayOfWeek)) {
                entryBuilder.day(DayAmount.builder().date(firstDayOfWeek).entries(getAppointmentsByDate(firstDayOfWeek, appointmentsByUser)).build());
                firstDayOfWeek = firstDayOfWeek.plusDays(1);
            }
            builder.entry(entryBuilder.build());
        }

        return builder.build();
    }

    private List<Entry> getAppointmentsByDate(LocalDate date, List<Appointment> appointments) {
        return appointments.stream().filter(appointment -> appointment.getDate().equals(date)).map(entryMapper::convertTransferObject)
                .toList();
    }
}
