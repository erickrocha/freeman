package com.erocha.freeman.time.usecases;

import com.erocha.freeman.time.domains.Appointment;
import com.erocha.freeman.time.domains.Status;
import com.erocha.freeman.time.gateways.AppointmentGateway;
import com.erocha.freeman.time.http.json.InputEntryTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class FinishAppointment {

    private static final LocalTime START_TIME = LocalTime.of(8, 00);

    private AppointmentGateway gateway;

    public FinishAppointment(AppointmentGateway gateway) {
        this.gateway = gateway;
    }

    public Appointment execute(InputEntryTO inputEntryTO) {
        if (inputEntryTO.getDate() == null) {
            inputEntryTO.setDate(LocalDate.now());
        }
        if (Status.STARTED.equals(inputEntryTO.getStatus()) && inputEntryTO.getId() != null && inputEntryTO.getEnd() == null) {
            Appointment appointment = Appointment.builder().id(inputEntryTO.getId()).userId(inputEntryTO.getUserId()).notes(inputEntryTO.getNotes())
                    .begin(inputEntryTO.getBegin()).end(LocalDateTime.now()).status(Status.SAVED).date(inputEntryTO.getDate())
                    .project(inputEntryTO.getProject()).build();
            return gateway.persist(appointment);
        }
        if (Status.PAUSED.equals(inputEntryTO.getStatus()) && inputEntryTO.getId() != null && inputEntryTO.getEnd() != null) {
            Appointment appointment = Appointment.builder().id(inputEntryTO.getId()).userId(inputEntryTO.getUserId()).notes(inputEntryTO.getNotes())
                    .begin(inputEntryTO.getBegin()).end(inputEntryTO.getEnd()).status(Status.SAVED).date(inputEntryTO.getDate())
                    .project(inputEntryTO.getProject()).build();
            return gateway.persist(appointment);
        }
        if (inputEntryTO.getHour() > 0 || inputEntryTO.getMinute() > 0) {
            LocalDateTime startDateTime = LocalDateTime.of(inputEntryTO.getDate(), START_TIME);
            Integer amountMinutes = (inputEntryTO.getHour() * 60) + inputEntryTO.getMinute();
            LocalDateTime endDateTime = startDateTime.plusMinutes(amountMinutes);
            Appointment appointment = Appointment.builder().id(inputEntryTO.getId()).userId(inputEntryTO.getUserId()).notes(inputEntryTO.getNotes()).begin(startDateTime).end(endDateTime)
                    .status(Status.SAVED).date(inputEntryTO.getDate()).project(inputEntryTO.getProject()).build();
            return gateway.persist(appointment);
        }
        return null;
    }
}
