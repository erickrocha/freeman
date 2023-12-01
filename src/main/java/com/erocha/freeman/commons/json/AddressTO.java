package com.erocha.freeman.commons.json;

import java.util.Optional;

import com.erocha.freeman.commons.domains.Province;
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
public class AddressTO {

	private String street;
	private String number;
	private String complement;
	private String neighborhood;
	private String city;
	private String zipCode;
	private Province province;

	public String getAddress() {
		StringBuilder builder = new StringBuilder();
		Optional.ofNullable(street).ifPresent(value -> builder.append(value).append(", "));
		Optional.ofNullable(number).ifPresent(value -> builder.append(value).append(", "));
		Optional.ofNullable(complement).ifPresent(value -> builder.append(value).append(", "));
		Optional.ofNullable(zipCode).ifPresent(value -> builder.append(value).append(", "));
		Optional.ofNullable(neighborhood).ifPresent(value -> builder.append(value).append(", "));
		Optional.ofNullable(city).ifPresent(value -> builder.append(value).append(", "));
		Optional.ofNullable(province.getName()).ifPresent(builder::append);
		return builder.toString();
	}
}
