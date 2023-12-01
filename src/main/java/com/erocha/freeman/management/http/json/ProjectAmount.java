package com.erocha.freeman.management.http.json;

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
public class ProjectAmount {

	private String id;
	private String name;
	private String owner;

	@Singular
	private List<PhaseAmount> phases;

}
