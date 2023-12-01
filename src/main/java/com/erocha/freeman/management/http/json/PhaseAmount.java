package com.erocha.freeman.management.http.json;

import com.erocha.freeman.commons.utils.UUIDGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhaseAmount {

	@Builder.Default
	private String id = UUIDGenerator.generate();
	private String phase;
	private Integer amountPendingInMinutes;
	private Integer amountApprovedInMinutes;
}
