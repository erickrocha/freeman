package com.erocha.freeman.dataload.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CsvAppointment implements IAppointment{

    private LocalDate date;
    private LocalTime start;
    private LocalTime end;
    private String name;
    private String owner;
    private String project;

}
