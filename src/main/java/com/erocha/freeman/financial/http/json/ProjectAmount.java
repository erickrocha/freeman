package com.erocha.freeman.financial.http.json;

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
public class ProjectAmount {

	private String projectId;
	private String projectName;
	private int amountApprovedInMinutes;
	private int amountApprovedInHour;
	private int amountPendingInMinutes;
	private int amountPendingInHour;
	private int pendingPercentage;
	private int approvedPercentage;

}
