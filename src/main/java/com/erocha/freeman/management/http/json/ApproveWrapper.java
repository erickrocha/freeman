package com.erocha.freeman.management.http.json;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApproveWrapper {

	private String userId;
	private LocalDate startDate;
	private LocalDate endDate;
	private List<String> items;
}
