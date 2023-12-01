package com.erocha.freeman.time.usecases;

import com.erocha.freeman.time.domains.Appointment;
import com.erocha.freeman.time.domains.Status;
import com.erocha.freeman.time.gateways.AppointmentGateway;
import com.erocha.freeman.time.http.json.InputEntryTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class StartStopTimer {

    private AppointmentGateway appointmentGateway;

    public StartStopTimer(AppointmentGateway appointmentGateway) {
        this.appointmentGateway = appointmentGateway;
    }

    public Appointment execute(InputEntryTO inputEntryTO) {
        if (inputEntryTO.getDate() == null) {
            inputEntryTO.setDate(LocalDate.now());
        }
        if (Status.STARTED.equals(inputEntryTO.getStatus()) && inputEntryTO.getId() != null && inputEntryTO.getEnd() == null) {
            Appointment appointment = Appointment.builder().id(inputEntryTO.getId()).userId(inputEntryTO.getUserId()).notes(inputEntryTO.getNotes()).begin(inputEntryTO.getBegin()).end(LocalDateTime.now())
                    .status(Status.PAUSED).date(inputEntryTO.getDate()).project(inputEntryTO.getProject()).build();
            return appointmentGateway.persist(appointment);
        }
        if (Status.PAUSED.equals(inputEntryTO.getStatus()) && inputEntryTO.getId() != null && inputEntryTO.getEnd() != null) {
            Appointment appointment = Appointment.builder().id(inputEntryTO.getId()).userId(inputEntryTO.getUserId()).notes(inputEntryTO.getNotes()).begin(LocalDateTime.now())
                    .status(Status.STARTED).date(inputEntryTO.getDate()).project(inputEntryTO.getProject()).build();
            return appointmentGateway.persist(appointment);
        }
        LocalDateTime starDateTime = LocalDateTime.now();
        if (inputEntryTO.getHour() > 0 || inputEntryTO.getMinute() > 0) {
            LocalDateTime endDateTime = LocalDateTime.now();
            Integer amountMinutes = (inputEntryTO.getHour() * 60) + inputEntryTO.getMinute();
            starDateTime = endDateTime.minusMinutes(amountMinutes);
        }
        Appointment appointment = Appointment.builder().userId(inputEntryTO.getUserId()).notes(inputEntryTO.getNotes()).begin(starDateTime)
                .status(Status.STARTED).date(inputEntryTO.getDate()).project(inputEntryTO.getProject()).build();

        return appointmentGateway.persist(appointment);
    }
}