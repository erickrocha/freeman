package com.erocha.freeman.reports.usecases;

import com.erocha.freeman.commons.exceptions.ResourceNotFoundException;
import com.erocha.freeman.hr.gateways.PersonGateway;
import com.erocha.freeman.management.domains.Project;
import com.erocha.freeman.reports.http.json.PhaseReportTO;
import com.erocha.freeman.reports.http.json.ProjectReportTO;
import com.erocha.freeman.reports.http.json.TimeReportWrapper;
import com.erocha.freeman.security.domains.User;
import com.erocha.freeman.security.gateways.UserGateway;
import com.erocha.freeman.time.domains.Appointment;
import com.erocha.freeman.time.domains.Status;
import com.erocha.freeman.time.gateways.AppointmentGateway;
import com.erocha.freeman.time.gateways.query.builder.AppointmentQueryBuilder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class GetTimeReport {


    private AppointmentGateway appointmentGateway;

    private UserGateway userGateway;

    private PersonGateway personGateway;

    public GetTimeReport(AppointmentGateway appointmentGateway, UserGateway userGateway, PersonGateway personGateway) {
        this.appointmentGateway = appointmentGateway;
        this.userGateway = userGateway;
        this.personGateway = personGateway;
    }

    public TimeReportWrapper execute(String username, LocalDate startDate, LocalDate endDate, String projectId, String phase) {
        TimeReportWrapper.TimeReportWrapperBuilder builder = TimeReportWrapper.builder();
        User me = userGateway.findByEmail(username).orElseThrow(ResourceNotFoundException::new);
        personGateway.findById(me.getId()).ifPresentOrElse(person -> builder.personName(person.getName()).personSocialSecurity(person.getSocialSecurity()), () -> builder.personName(me.getName()));
        builder.startDate(startDate).endDate(endDate);
        List<Appointment> appointments = appointmentGateway.query(AppointmentQueryBuilder.queryByParams(startDate, endDate, me.getId(), projectId, phase, null)).toList();
        appointments.stream().map(Appointment::getProject).collect(Collectors.toSet()).stream().forEach(project -> builder.project(getProject(appointments, project)));
        return builder.build();
    }

    private ProjectReportTO getProject(List<Appointment> appointments, Project project) {
        ProjectReportTO.ProjectReportTOBuilder builder = ProjectReportTO.builder();
        builder.id(project.getId()).name(project.getName());
        Optional.ofNullable(project.getOwner()).ifPresent(owner -> builder.owner(owner.getName()));
        builder.totalPendingInMinutes(getTotalInMinutes(appointments.stream().toList(), Status.SAVED)).totalApprovedInMinutes(getTotalInMinutes(appointments.stream().toList(), Status.APPROVED));
        project.getPhases().forEach(phase -> {
            List<Appointment> phaseStream = appointments.stream().filter(filterByPhase(phase)).toList();
            builder.phase(PhaseReportTO.builder().name(phase).totalApprovedInMinutes(getTotalInMinutes(phaseStream, Status.APPROVED)).totalPendingInMinutes(getTotalInMinutes(phaseStream, Status.SAVED)).build());
        });
        return builder.build();

    }

    private Integer getTotalInMinutes(List<Appointment> appointmentStream, Status status) {
        return appointmentStream.stream().filter(filterByStatus(status)).mapToInt(Appointment::getAmountInMinutes).sum();
    }

    private Predicate<Appointment> filterByStatus(Status status) {
        return appointment -> status.equals(appointment.getStatus());
    }

    private Predicate<Appointment> filterByPhase(String phase) {
        return appointment -> appointment.getProject().getPhases().contains(phase);
    }
}
