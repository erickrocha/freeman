package com.erocha.freeman.commons.domains;

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
public class Address {

	private String street;
	private String number;
	private String complement;
	private String neighborhood;
	private String city;
	private String zipCode;
	private Province province;
}
