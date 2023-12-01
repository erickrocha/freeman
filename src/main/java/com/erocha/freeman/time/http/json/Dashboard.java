package com.erocha.freeman.time.http.json;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Dashboard {

    private Integer year;
    private Integer monthOfYear;
    private Month month;
    private LocalDate startDate;
    private LocalDate endDate;


    private List<DayAmount> days;

    public void addDaysAmount(DayAmount dayAmount) {
        if (this.days == null) {
            this.days = new ArrayList<>();
        }
        this.days.add(dayAmount);
    }
}
