package com.erocha.freeman.management.http.json;

import java.time.LocalDate;

import com.erocha.freeman.time.domains.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Entry {

	private String id;
	private String userName;
	private String userId;
	private LocalDate date;
	private ProjectTO project;
	private Status status;
	private int hour;
	private int minute;
	private String notes;
	private boolean selected;

	public String getTime() {
		return hour+ "h "+minute+"m";
	}
}
