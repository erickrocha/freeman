package com.erocha.freeman.financial.http.json;

import java.time.LocalDate;
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
public class PaymentDashboard {

	private LocalDate startDate;
	private LocalDate endDate;

	@Singular
	private List<Payment> payments;
}
