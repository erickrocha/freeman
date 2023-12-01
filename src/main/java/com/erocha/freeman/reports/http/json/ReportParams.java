package com.erocha.freeman.reports.http.json;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
public class ReportParams {

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate startDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate endDate;
	private String project;
	private String phase;
	private String userId;
}
