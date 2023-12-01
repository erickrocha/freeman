package com.erocha.freeman.supply.http.json;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
public class SupplierParams {

	private String legalNumber;
	private String legalName;

	public boolean hasFilterValues() {
		return (legalName != null && StringUtils.isNotEmpty(legalName)) || (legalNumber != null && StringUtils.isNotEmpty(legalNumber));
	}
}
