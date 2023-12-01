package com.erocha.freeman.financial.http.json;

import java.util.List;

import com.erocha.freeman.commons.utils.UUIDGenerator;
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
public class Payment {

	@Builder.Default
	private String id = UUIDGenerator.generate();
	private String personId;
	private String personName;
	private String personSocialSecurity;

	@Builder.Default
	private boolean selected = false;

	@Singular
	private List<ProjectAmount> projects;
}
