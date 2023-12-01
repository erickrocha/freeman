package com.erocha.freeman.reports.http.json;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

import com.erocha.freeman.commons.utils.UUIDGenerator;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimeAmount {

	private String id = UUIDGenerator.generate();
	private LocalDate date;
	private Integer hour;
	private Integer minute;

	public TimeAmount(LocalDate date, Integer amount) {
		this.date = date;
		this.hour = amount / 60;
		this.minute = amount % 60;
	}

	public String getDayOfWeek() {
		return this.date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.getDefault());
	}
}
