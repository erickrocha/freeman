package com.erocha.freeman.reports.http.json;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectReportTO {

	private String id;
	private String name;
	private String owner;
	private Integer totalPendingInMinutes;
	private Integer totalApprovedInMinutes;

	@Singular
	private List<PhaseReportTO> phases;

}
