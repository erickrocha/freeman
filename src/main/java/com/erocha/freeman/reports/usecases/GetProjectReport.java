package com.erocha.freeman.reports.usecases;

import com.erocha.freeman.commons.exceptions.BusinessException;
import com.erocha.freeman.commons.exceptions.ResourceNotFoundException;
import com.erocha.freeman.management.domains.Project;
import com.erocha.freeman.management.gateways.ProjectGateway;
import com.erocha.freeman.reports.http.json.PhaseReportTO;
import com.erocha.freeman.reports.http.json.ProjectReportTO;
import com.erocha.freeman.reports.http.json.ProjectReportWrapper;
import com.erocha.freeman.security.domains.User;
import com.erocha.freeman.security.gateways.UserGateway;
import com.erocha.freeman.time.domains.Appointment;
import com.erocha.freeman.time.domains.Status;
import com.erocha.freeman.time.gateways.AppointmentGateway;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Service
public class GetProjectReport {


    private AppointmentGateway appointmentGateway;

    private UserGateway userGateway;

    private ProjectGateway projectGateway;

    public GetProjectReport(AppointmentGateway appointmentGateway, UserGateway userGateway, ProjectGateway projectGateway) {
        this.appointmentGateway = appointmentGateway;
        this.userGateway = userGateway;
        this.projectGateway = projectGateway;
    }

    public ProjectReportWrapper execute(String projectId, LocalDate startDate, LocalDate endDate, String username) throws BusinessException {
        ProjectReportWrapper.ProjectReportWrapperBuilder builder = ProjectReportWrapper.builder();
        builder.startDate(startDate).endDate(endDate);
        if (Objects.nonNull(projectId)) {
            Project project = projectGateway.findById(projectId).orElseThrow(ResourceNotFoundException::new);
            builder.project(getProject(project, startDate, endDate));
        } else {
            User me = userGateway.findByEmail(username).orElseThrow(ResourceNotFoundException::new);
            Stream<Project> projectStream = projectGateway.findAllByManagerId(me.getId());
            projectStream.forEach(project -> builder.project(getProject(project, startDate, endDate)));
        }
        return builder.build();
    }

    private ProjectReportTO getProject(Project project, LocalDate startDate, LocalDate endDate) {

        ProjectReportTO.ProjectReportTOBuilder builder = ProjectReportTO.builder();
        builder.id(project.getId()).name(project.getName());
        Optional.ofNullable(project.getOwner()).ifPresent(owner -> builder.owner(owner.getName()));

        List<Appointment> appointments = appointmentGateway.findByProjectIdAndDateBetween(project.getId(), startDate, endDate).toList();
        builder.totalPendingInMinutes(getTotalInMinutes(appointments, Status.SAVED)).totalApprovedInMinutes(getTotalInMinutes(appointments, Status.APPROVED));

        project.getPhases().stream().forEach(phase -> {
            List<Appointment> phaseStream = appointments.stream().filter(filterByPhase(phase)).toList();
            builder.phase(PhaseReportTO.builder().name(phase).totalApprovedInMinutes(getTotalInMinutes(phaseStream, Status.APPROVED))
                    .totalPendingInMinutes(getTotalInMinutes(phaseStream, Status.SAVED)).build());
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
