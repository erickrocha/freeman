package com.erocha.freeman.crm.http.json;

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
public class CustomerTO {

	private String id;
	private LocalDate dateRegister;
	private String legalNumber;
	private String legalName;
	private String businessName;
	private AddressTO address;
	private String phone;
	private String email;
	private String website;
	@Builder.Default
	private boolean selected = false;
}
