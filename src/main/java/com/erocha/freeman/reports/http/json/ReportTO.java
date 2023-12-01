package com.erocha.freeman.reports.http.json;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportTO {

	private LocalDate startDate;
	private LocalDate endDate;
	private List<TimeAmount> dailyAmount;
	private List<ProjectAmount> projectsAmounts;
	private Long hour;
	private Long minute;
	private Long totalApprovedHour;
	private Long totalApprovedMinutes;
	private Long totalUnapprovedHour;
	private Long totalUnapprovedMinutes;
}
