package com.erocha.freeman.management.http.mapper;

import com.erocha.freeman.commons.mapper.Mapper;
import com.erocha.freeman.management.http.json.Entry;
import com.erocha.freeman.time.domains.Appointment;
import org.springframework.stereotype.Component;

@Component
public class EntryMapper implements Mapper<Appointment, Entry> {

    private ProjectMapper projectMapper;

    public EntryMapper(ProjectMapper projectMapper) {
        this.projectMapper = projectMapper;
    }

    @Override
    public Entry convertTransferObject(Appointment appointment) {
        return Entry.builder().id(appointment.getId()).date(appointment.getDate()).hour(appointment.getHour()).minute(appointment.getHour())
                .userId(appointment.getUserId()).status(appointment.getStatus()).notes(appointment.getNotes())
                .project(projectMapper.convertTransferObject(appointment.getProject())).selected(false).build();
    }

    @Override
    public Appointment convertEntity(Entry entry) {
        return null;
    }
}
