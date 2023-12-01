package com.erocha.freeman.crm.domains;

import java.time.LocalDate;

import com.erocha.freeman.commons.domains.Address;
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
public class Customer {

	@Builder.Default
	protected String id = UUIDGenerator.generate();

	protected LocalDate dateRegister;

	protected String legalNumber;

	protected String legalName;

	protected String email;

	protected String phone;

	protected String website;

	protected String businessName;

	protected Address address;

}
