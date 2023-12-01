package com.erocha.freeman.time.http.json;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.erocha.freeman.management.domains.Project;
import com.erocha.freeman.time.domains.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InputEntryTO {

	private String id;
	private String userId;

	private LocalDate date;
	private Project project;
	private Integer hour;
	private Integer minute;
	private LocalDateTime begin;
	private LocalDateTime end;

	private Status status;

	private String notes;
}
