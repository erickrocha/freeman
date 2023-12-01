package com.erocha.freeman.time.http.json;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import com.erocha.freeman.management.http.json.Entry;
import com.erocha.freeman.time.domains.Appointment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DayAmount {

	private LocalDate date;
	private List<Entry> entries;
	private List<Appointment> appointments;
	private boolean selected;

	public String getDayOfWeek() {
		return this.date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.getDefault());
	}

	public Integer getDayOfMonth() {
		return this.date.getDayOfMonth();
	}

	public Month getMonth() {
		return this.date.getMonth();
	}
}
