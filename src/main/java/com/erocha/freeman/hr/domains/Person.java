package com.erocha.freeman.hr.domains;

import java.time.LocalDate;

import com.erocha.freeman.commons.domains.Address;
import com.erocha.freeman.security.domains.Profile;
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
public class Person {

	private String id;

	@Builder.Default
	private LocalDate registerDate = LocalDate.now();
	private String name;
	private String socialSecurity;
	private Profile profile;
	private LocalDate birthDate;
	private Avatar avatar;
	private Address address;
	private String phone;
	private String email;

}
