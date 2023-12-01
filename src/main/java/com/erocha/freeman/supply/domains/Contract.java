package com.erocha.freeman.supply.domains;

import java.time.LocalDate;

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
public class Contract {

	@Builder.Default
	private LocalDate dateRegister = LocalDate.now();
	private LocalDate startDate;
	private LocalDate endDate;
	private Long valuePerHourInCents;
	private String number;

}
