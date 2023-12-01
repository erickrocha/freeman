package com.erocha.freeman.security.http.json;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {

	private String id;
	private String name;
	private String email;
	private String profile;
}
