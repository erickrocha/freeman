package com.erocha.freeman.time.usecases;

import com.erocha.freeman.commons.utils.DateTools;
import com.erocha.freeman.time.domains.Appointment;
import com.erocha.freeman.time.gateways.AppointmentGateway;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

@Service
public class GetAppointment {

    private AppointmentGateway gateway;

    public GetAppointment(AppointmentGateway gateway) {
        this.gateway = gateway;
    }

    public List<Appointment> execute() {
        return gateway.findAll();
    }

    public Stream<Appointment> execute(String userId, LocalDate startDate, LocalDate endDate) {
        if (startDate == null) {
            LocalDate today = LocalDate.now();
            startDate = DateTools.findFirstDayOfWeek(today, DayOfWeek.SUNDAY);
            endDate = DateTools.findLastDayOfWeek(today, DayOfWeek.SATURDAY);
        }
        return gateway.findByUserIdAndDateBetween(userId, startDate, endDate);
    }

    public Stream<Appointment> execute(Query query) {
        return gateway.query(query);
    }

    public List<Appointment> execute(String userId) {
        return gateway.findAllByUserId(userId);
    }
}
