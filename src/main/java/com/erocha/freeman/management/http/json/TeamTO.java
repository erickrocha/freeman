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
public class TeamTO {

	private String id;
	private String name;
	private MemberTO manager;
	private MemberTO techLead;
	@Singular
	private List<MemberTO> members;
}
