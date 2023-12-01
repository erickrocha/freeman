package com.erocha.freeman.reports.http.json;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ProjectReportWrapper {

	private LocalDate startDate;
	private java.time.LocalDate endDate;

	@Singular
	private List<ProjectReportTO> projects;
}
