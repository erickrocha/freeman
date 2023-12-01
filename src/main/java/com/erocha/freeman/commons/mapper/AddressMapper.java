package com.erocha.freeman.commons.mapper;

import com.erocha.freeman.commons.domains.Address;
import com.erocha.freeman.commons.json.AddressTO;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper implements Mapper<Address, AddressTO> {

	@Override
	public AddressTO convertTransferObject(Address address) {
		return AddressTO.builder().street(address.getStreet())
				.number(address.getNumber())
				.complement(address.getComplement())
				.zipCode(address.getZipCode())
				.neighborhood(address.getNeighborhood())
				.city(address.getCity())
				.province(address.getProvince())
				.build();
	}

	@Override
	public Address convertEntity(AddressTO to) {
		return Address.builder()
				.street(to.getStreet())
				.number(to.getNumber())
				.complement(to.getComplement())
				.zipCode(to.getZipCode())
				.neighborhood(to.getNeighborhood())
				.city(to.getCity())
				.province(to.getProvince())
				.build();
	}
}
