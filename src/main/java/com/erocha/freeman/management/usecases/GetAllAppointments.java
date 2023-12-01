package com.erocha.freeman.management.usecases;

import com.erocha.freeman.management.http.json.Entry;
import com.erocha.freeman.management.http.mapper.EntryAggregateMapper;
import com.erocha.freeman.security.domains.User;
import com.erocha.freeman.security.gateways.UserGateway;
import com.erocha.freeman.time.gateways.AppointmentGateway;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class GetAllAppointments {


    private AppointmentGateway gateway;

    private UserGateway userGateway;

    private EntryAggregateMapper entryAggregateMapper;

    public GetAllAppointments(AppointmentGateway gateway, UserGateway userGateway, EntryAggregateMapper entryAggregateMapper) {
        this.gateway = gateway;
        this.userGateway = userGateway;
        this.entryAggregateMapper = entryAggregateMapper;
    }

    public Page<Entry> execute(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        List<User> users = collectUsers();
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").in(users.stream().map(User::getId).toList()));
        query.addCriteria(Criteria.where("date").lte(endDate).gte(startDate));
        return entryAggregateMapper.convertPaginated(gateway.query(query, pageable), users);
    }

    private List<User> collectUsers() {
        return userGateway.findAll();
    }
}
