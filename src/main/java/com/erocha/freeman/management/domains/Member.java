package com.erocha.freeman.management.domains;

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
public class Member {

	private String id;
	private String name;
	@Builder.Default
	private boolean lead = false;
}
