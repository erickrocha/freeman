package com.erocha.freeman.dataload.usecases;

import com.erocha.freeman.dataload.domain.CsvAppointment;
import com.erocha.freeman.dataload.domain.IAppointment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CsvLoader {

    public List<IAppointment> execute(String text) {
        String[] array = text.split("\n");
        return execute(List.of(array));
    }

    public List<IAppointment> execute(List<String> source) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        List<IAppointment> appointments = new ArrayList<>();

        source.forEach(line -> {
            String[] lineSplit = line.trim().split(",");
            LocalDate date = LocalDate.parse(lineSplit[0], dateFormatter);
            LocalTime startTime = LocalTime.parse(lineSplit[6], timeFormatter);
            LocalTime endTime = LocalTime.parse(lineSplit[7], timeFormatter);
            CsvAppointment appointment = new CsvAppointment(date, startTime, endTime, lineSplit[5], lineSplit[1], lineSplit[2]);
            appointments.add(appointment);
        });

        return appointments;
    }
}
