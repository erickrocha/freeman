package com.erocha.freeman.reports.http.json;

import com.erocha.freeman.management.domains.Project;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectAmount {

	private Project project;
	private Integer hour;
	private Integer minute;

	public ProjectAmount(Project project, Integer amountInMinutes) {
		this.project = project;
		this.hour = amountInMinutes / 60;
		this.minute = amountInMinutes % 60;
	}
}
