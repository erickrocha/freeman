package com.erocha.freeman.security.http.json;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePasswordRequest {

	private String password;
	private String confirmPassword;
}
