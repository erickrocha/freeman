package com.erocha.freeman.supply.http.json;

import java.time.LocalDate;
import java.util.List;

import com.erocha.freeman.commons.json.AddressTO;
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
public class SupplierTO {

	private String id;
	protected LocalDate dateRegister;
	private String legalNumber;
	private String legalName;
	private String businessName;
	private AddressTO address;
	private String phone;
	private String email;
	private String website;
	private String responsibleName;
	private boolean active;
	@Builder.Default
	private boolean selected = false;

	private ContractTO contract;

	@Singular
	private List<EmployeeTO> employees;
}
