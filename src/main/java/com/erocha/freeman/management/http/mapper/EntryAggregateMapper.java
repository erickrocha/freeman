package com.erocha.freeman.management.http.mapper;

import com.erocha.freeman.commons.mapper.MapperAggregate;
import com.erocha.freeman.management.http.json.Entry;
import com.erocha.freeman.security.domains.User;
import com.erocha.freeman.time.domains.Appointment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class EntryAggregateMapper implements MapperAggregate<Appointment, Entry, List<User>> {

    private ProjectMapper projectMapper;

    public EntryAggregateMapper(ProjectMapper projectMapper) {
        this.projectMapper = projectMapper;
    }

    @Override
    public Entry convertTransferObject(Appointment appointment, List<User> users) {
        return Entry.builder().id(appointment.getId()).date(appointment.getDate()).hour(appointment.getHour()).minute(appointment.getHour())
                .userId(appointment.getUserId()).status(appointment.getStatus()).notes(appointment.getNotes())
                .userName(collectUserName(users, appointment.getUserId())).project(projectMapper.convertTransferObject(appointment.getProject()))
                .selected(false).build();

    }

    private String collectUserName(List<User> users, String userId) {
        Optional<User> user = users.stream().filter(user1 -> userId.equals(user1.getId())).findFirst();
        return user.isPresent() ? user.get().getName() : "";
    }
}
