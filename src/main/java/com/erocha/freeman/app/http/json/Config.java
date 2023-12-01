package com.erocha.freeman.app.http.json;

import java.util.List;

import com.erocha.freeman.app.domains.Menu;
import com.erocha.freeman.commons.domains.Province;
import com.erocha.freeman.hr.http.json.PersonTO;
import com.erocha.freeman.management.http.json.MemberTO;
import com.erocha.freeman.management.http.json.OwnerTO;
import com.erocha.freeman.management.http.json.ProjectManagerTO;
import com.erocha.freeman.management.http.json.ProjectTO;
import com.erocha.freeman.security.domains.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Config {

	private User user;

	private PersonTO person;

	private List<Province> provinces;

	private List<Menu> menus;

	private List<ProjectTO> myProjects;

	private List<ProjectManagerTO> managers;

	private List<ProfileTO> profiles;

	private List<OwnerTO> owners;

	private List<MemberTO> players;

	private List<MemberTO> techLeads;
}
