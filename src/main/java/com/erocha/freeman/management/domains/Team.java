package com.erocha.freeman.management.domains;

import java.util.List;

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
public class Team {

	private String id;
	private String name;
	private Member manager;

	@Singular
	private List<Member> members;
}
