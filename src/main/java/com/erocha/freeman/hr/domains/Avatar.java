package com.erocha.freeman.hr.domains;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Avatar {

	private String suffix;
	private byte[] image;

	public Avatar(String suffix, byte[] image) {
		this.suffix = suffix;
		this.image = image;
	}
}
