package com.erocha.freeman.commons.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class DateToolsTest {

    @Test
    void test_first_Day_of_week() {
        LocalDate firstDayOfWeek = DateTools.findFirstDayOfWeek(LocalDate.now(), DayOfWeek.MONDAY);
        assertAll("Test if it is the first day of week",
                () -> assertEquals(DayOfWeek.MONDAY, firstDayOfWeek.getDayOfWeek()));
    }

    @Test
    void test_last_Day_of_week() {
        LocalDate firstDayOfWeek = DateTools.findFirstDayOfWeek(LocalDate.now(), DayOfWeek.FRIDAY);
        assertAll("Test if it is the last day of week",
                () -> assertEquals(DayOfWeek.FRIDAY, firstDayOfWeek.getDayOfWeek()));
    }

    @Test
    void test_first_Day_of_month() {
        LocalDate firstDayOfMonth = DateTools.findFirstDayOfMonth();
        assertAll("Test if it is the first day of month",
                () -> assertEquals(1, firstDayOfMonth.getDayOfMonth()));
    }

    @Test
    void test_last_Day_of_month() {
        LocalDate lastDayOfMonth = DateTools.findLastDayOfMonth();
        assertAll("Test if it is the last day of month",
                () -> assertEquals(LocalDate.now().lengthOfMonth(), lastDayOfMonth.lengthOfMonth()));
    }
}
