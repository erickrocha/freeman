package com.erocha.freeman.financial.http.json;

import java.util.List;

import com.erocha.freeman.commons.utils.UUIDGenerator;
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
public class Order {

	@Builder.Default
	private String id = UUIDGenerator.generate();
	private String customerId;
	private String customerName;
	private String customerNumber;

	@Singular
	private List<ProjectAmount> projects;
}
