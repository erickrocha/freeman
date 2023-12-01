package com.erocha.freeman.supply.http.json;

import java.time.LocalDate;

import com.erocha.freeman.commons.json.AddressTO;
import com.erocha.freeman.commons.utils.UUIDGenerator;
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
public class EmployeeTO {

	@Builder.Default
	private String id = UUIDGenerator.generate();

	@Builder.Default
	private boolean responsible = false;
	private LocalDate registerDate;
	private String name;
	private String socialSecurity;
	private String profile;
	private LocalDate birthDate;
	private String avatarBase64;
	private AddressTO address;
	private String phone;
	private String email;
}
