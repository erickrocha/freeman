package com.erocha.freeman.time.usecases;

import com.erocha.freeman.time.domains.Appointment;
import com.erocha.freeman.time.gateways.AppointmentGateway;
import com.erocha.freeman.time.http.json.InputEntryTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class UpdateAppointment {


    private AppointmentGateway gateway;

    public UpdateAppointment(AppointmentGateway gateway) {
        this.gateway = gateway;
    }

    public Appointment execute(InputEntryTO inputEntry) {
        if (inputEntry.getDate() == null) {
            inputEntry.setDate(LocalDate.now());
        }
        LocalDateTime endDateTime = LocalDateTime.now();
        Integer amountMinutes = (inputEntry.getHour() * 60) + inputEntry.getMinute();
        LocalDateTime starDateTime = endDateTime.minusMinutes(amountMinutes);
        Appointment appointment = Appointment.builder()
                .id(inputEntry.getId())
                .userId(inputEntry.getUserId())
                .notes(inputEntry.getNotes())
                .begin(starDateTime)
                .end(endDateTime)
                .status(inputEntry.getStatus())
                .date(inputEntry.getDate())
                .project(inputEntry.getProject())
                .build();
        return gateway.persist(appointment);
    }
}
