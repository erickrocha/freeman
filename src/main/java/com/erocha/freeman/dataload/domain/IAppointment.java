package com.erocha.freeman.dataload.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public interface IAppointment {

    LocalDate getDate();

    LocalTime getStart();

    LocalTime getEnd();

    String getName();

    String getProject();

    String getOwner();
}
