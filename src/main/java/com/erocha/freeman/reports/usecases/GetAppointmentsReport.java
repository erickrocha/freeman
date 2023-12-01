package com.erocha.freeman.reports.usecases;

import com.erocha.freeman.time.domains.Appointment;
import com.erocha.freeman.time.gateways.AppointmentGateway;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.stream.Stream;

@Service
public class GetAppointmentsReport {

    private AppointmentGateway gateway;

    public GetAppointmentsReport(AppointmentGateway gateway) {
        this.gateway = gateway;
    }

    public Stream<Appointment> execute(LocalDate startDate, LocalDate endDate, String projectId, String phase, String userId) {

        Query query = new Query();
        Criteria requiredCriteria = Criteria.where("userId").is(userId);
        query.addCriteria(requiredCriteria);
        if (startDate != null && endDate != null) {
            Criteria criteria = Criteria.where("date").lte(endDate).gte(startDate);
            query.addCriteria(criteria);
        }
        if (projectId != null && !projectId.isEmpty()) {
            Criteria criteria = Criteria.where("project.id").is(projectId);
            query.addCriteria(criteria);
        }
        if (phase != null && !phase.isEmpty()) {
            Criteria criteria = Criteria.where("project.phases").is(phase);
            query.addCriteria(criteria);
        }
        return gateway.query(query);
    }
}
