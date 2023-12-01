package com.erocha.freeman.management.http.json;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
public class ManagerDashboardParams {

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate startDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate endDate;
}
