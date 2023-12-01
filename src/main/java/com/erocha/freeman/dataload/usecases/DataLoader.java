package com.erocha.freeman.dataload.usecases;

import com.erocha.freeman.commons.exceptions.ResourceNotFoundException;
import com.erocha.freeman.dataload.domain.IAppointment;
import com.erocha.freeman.management.domains.Project;
import com.erocha.freeman.management.gateways.ProjectGateway;
import com.erocha.freeman.time.domains.Appointment;
import com.erocha.freeman.time.domains.Status;
import com.erocha.freeman.time.gateways.AppointmentGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class DataLoader {


    private ProjectGateway projectGateway;

    private AppointmentGateway appointmentGateway;

    private CsvLoader csvLoader;

    @Autowired
    public DataLoader(CsvLoader csvLoader,ProjectGateway projectGateway, AppointmentGateway appointmentGateway) {
        this.csvLoader = csvLoader;
        this.projectGateway = projectGateway;
        this.appointmentGateway = appointmentGateway;
    }

    public List<String> execute(String text) {
        List<IAppointment> appointments = csvLoader.execute(text);
        return execute(appointments);
    }

    public List<String> execute(List<IAppointment> appointments) {
        List<String> response = new ArrayList<>();
        appointments.forEach(source -> {
            Project project = projectGateway.findByName(source.getProject()).orElseThrow(ResourceNotFoundException::new);
            Set<String> phases = new HashSet<>();
            phases.add("Desenvolvimento");
            project.setPhases(phases);
            String line = "Imported to ".concat(source.getOwner()).concat(" at ").concat(source.getDate().format(DateTimeFormatter.ISO_DATE));
            Appointment appointment = Appointment.builder()
                    .date(source.getDate())
                    .project(project)
                    .status(Status.APPROVED)
                    .userId("85ebd221-65a2-4447-98ed-9b4e46e2171e")
                    .begin(LocalDateTime.of(source.getDate(), source.getStart()))
                    .end(LocalDateTime.of(source.getDate(), source.getEnd()))
                    .notes(line)
                    .build();
            appointmentGateway.persist(appointment);
            response.add(line);
        });
        return response;
    }
}
