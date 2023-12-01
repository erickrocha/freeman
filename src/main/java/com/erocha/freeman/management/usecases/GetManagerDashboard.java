package com.erocha.freeman.management.usecases;

import com.erocha.freeman.commons.exceptions.ResourceNotFoundException;
import com.erocha.freeman.management.domains.Project;
import com.erocha.freeman.management.gateways.ProjectGateway;
import com.erocha.freeman.management.gateways.query.builder.ProjectQueryBuilder;
import com.erocha.freeman.management.http.json.ManagerDashboard;
import com.erocha.freeman.management.http.json.PhaseAmount;
import com.erocha.freeman.management.http.json.ProjectAmount;
import com.erocha.freeman.security.domains.User;
import com.erocha.freeman.security.gateways.UserGateway;
import com.erocha.freeman.time.domains.Appointment;
import com.erocha.freeman.time.domains.Status;
import com.erocha.freeman.time.gateways.AppointmentGateway;
import com.erocha.freeman.time.gateways.query.builder.AppointmentQueryBuilder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class GetManagerDashboard {


    private AppointmentGateway gateway;

    private UserGateway userGateway;

    private ProjectGateway projectGateway;

    public GetManagerDashboard(AppointmentGateway gateway, UserGateway userGateway, ProjectGateway projectGateway) {
        this.gateway = gateway;
        this.userGateway = userGateway;
        this.projectGateway = projectGateway;
    }

    public ManagerDashboard execute(String username, LocalDate startDate, LocalDate endDate) {
        User me = userGateway.findByEmail(username).orElseThrow(ResourceNotFoundException::new);
        List<Project> projects = projectGateway.query(ProjectQueryBuilder.queryByProjectManager(me.getId()));
        ManagerDashboard.ManagerDashboardBuilder dashboardBuilder = ManagerDashboard.builder();
        projects.forEach(project -> {
            ProjectAmount.ProjectAmountBuilder projectAmountBuilder = ProjectAmount.builder();
            projectAmountBuilder.id(project.getId()).name(project.getName()).owner(project.getOwner().getName());
            List<Appointment> appointments = gateway.query(AppointmentQueryBuilder.queryByProjectAndDateBetween(project.getId(), startDate, endDate))
                    .toList();
            project.getPhases().forEach(phase -> projectAmountBuilder.phase(getPhaseAmount(appointments, phase)));
            dashboardBuilder.project(projectAmountBuilder.build());
        });
        return dashboardBuilder.build();
    }

    private PhaseAmount getPhaseAmount(List<Appointment> appointments, String phase) {
        PhaseAmount.PhaseAmountBuilder builder = PhaseAmount.builder();
        Integer amountPending = appointments.stream()
                .filter(appointment -> appointment.getProject().getPhases().contains(phase) && Status.SAVED.equals(appointment.getStatus()))
                .mapToInt(Appointment::getAmountInMinutes).sum();
        Integer amountApproved = appointments.stream()
                .filter(appointment -> appointment.getProject().getPhases().contains(phase) && Status.APPROVED.equals(appointment.getStatus()))
                .mapToInt(Appointment::getAmountInMinutes).sum();
        builder.phase(phase).amountPendingInMinutes(amountPending).amountApprovedInMinutes(amountApproved);
        return builder.build();
    }
}
