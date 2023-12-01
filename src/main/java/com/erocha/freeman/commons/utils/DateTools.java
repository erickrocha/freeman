package com.erocha.freeman.commons.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class DateTools {

	private DateTools() {
	}

	public static LocalDate findFirstDayOfWeek(LocalDate current, DayOfWeek dayOfWeek) {
		if (current.getDayOfWeek().equals(dayOfWeek)) {
			return current;
		} else {
			LocalDate prior = current.minusDays(1);
			return findFirstDayOfWeek(prior, dayOfWeek);
		}
	}

	public static LocalDate findLastDayOfWeek(LocalDate current, DayOfWeek dayOfWeek) {
		if (current.getDayOfWeek().equals(dayOfWeek)) {
			return current;
		} else {
			LocalDate prior = current.plusDays(1);
			return findLastDayOfWeek(prior, dayOfWeek);
		}
	}

	public static LocalDate findFirstDayOfMonth() {
		return LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
	}

	public static LocalDate findLastDayOfMonth() {
		return LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), LocalDate.now().lengthOfMonth());
	}
}
