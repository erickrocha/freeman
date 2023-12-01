package com.erocha.freeman.reports.http.json;

import com.erocha.freeman.commons.utils.UUIDGenerator;
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
public class PhaseReportTO {

	@Builder.Default
	private String id = UUIDGenerator.generate();
	private String name;
	private Integer totalPendingInMinutes;
	private Integer totalApprovedInMinutes;

}
