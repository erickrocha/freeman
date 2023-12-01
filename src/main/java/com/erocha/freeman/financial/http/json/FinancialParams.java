package com.erocha.freeman.financial.http.json;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
public class FinancialParams {

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate startDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate endDate;
}
