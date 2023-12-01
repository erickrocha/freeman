package com.erocha.freeman.management.http.json;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EntriesParams {

    private LocalDate startDate;
    private LocalDate endDate;
}
