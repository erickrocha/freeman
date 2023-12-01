package com.erocha.freeman.management.usecases;

import com.erocha.freeman.management.http.json.Entry;
import com.erocha.freeman.management.http.mapper.EntryAggregateMapper;
import com.erocha.freeman.security.domains.User;
import com.erocha.freeman.security.gateways.UserGateway;
import com.erocha.freeman.time.domains.Appointment;
import com.erocha.freeman.time.domains.Status;
import com.erocha.freeman.time.gateways.AppointmentGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
@Slf4j
public class ManageAppointment {


    private AppointmentGateway gateway;

    private UserGateway userGateway;

    private EntryAggregateMapper entryAggregateMapper;

    public ManageAppointment(AppointmentGateway gateway, UserGateway userGateway, EntryAggregateMapper entryAggregateMapper) {
        this.gateway = gateway;
        this.userGateway = userGateway;
        this.entryAggregateMapper = entryAggregateMapper;
    }

    public List<Entry> execute(List<String> ids) {
        Stream<Appointment> appointments = gateway.query(buildQuery(ids));
        appointments.forEach(appointment -> {
            if (Status.APPROVED.equals(appointment.getStatus())) {
                appointment.setStatus(Status.CLOSED);
            }
            if (Status.SAVED.equals(appointment.getStatus())) {
                appointment.setStatus(Status.APPROVED);
            }
        });
        List<Appointment> updated = gateway.saveAll(appointments.toList());
        Query query = new Query();
        query.addCriteria(Criteria.where("id").in(updated.stream().map(Appointment::getUserId).toList()));
        List<User> users = userGateway.query(query);
        return entryAggregateMapper.convertTransferObject(updated, users);
    }

    private Query buildQuery(List<String> ids) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").in(ids));
        return query;
    }
}
