package com.erocha.freeman.management.http.json;

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
public class MemberTO {

	private String id;
	private String name;
	private String profile;
	@Builder.Default
	private boolean lead = false;

}
