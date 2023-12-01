package com.erocha.freeman.hr.http.json;

import java.time.LocalDate;

import com.erocha.freeman.commons.json.AddressTO;
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
public class PersonTO {

	private String id;

	private LocalDate registerDate;
	private String name;
	private String socialSecurity;
	private LocalDate birthDate;
	private String avatarBase64;
	private AddressTO address;
	private String phone;
	private String email;
}
