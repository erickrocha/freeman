package com.erocha.freeman.management.http.mapper;

import java.util.Optional;

import com.erocha.freeman.commons.mapper.Mapper;
import com.erocha.freeman.management.domains.Owner;
import com.erocha.freeman.management.domains.Project;
import com.erocha.freeman.management.domains.ProjectManager;
import com.erocha.freeman.management.http.json.OwnerTO;
import com.erocha.freeman.management.http.json.ProjectManagerTO;
import com.erocha.freeman.management.http.json.ProjectTO;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper implements Mapper<Project, ProjectTO> {

	@Override
	public ProjectTO convertTransferObject(Project project) {
		ProjectTO.ProjectTOBuilder builder = ProjectTO.builder();
		builder.id(project.getId()).name(project.getName()).code(project.getCode()).phases(project.getPhases()).active(project.isActive()).build();
		Optional.ofNullable(project.getOwner()).ifPresent(owner -> builder.owner(OwnerTO.builder().id(owner.getId()).name(owner.getName()).build()));
		Optional.ofNullable(project.getManager()).ifPresent(pm -> builder.manager(ProjectManagerTO.builder().id(pm.getId()).name(pm.getName()).build()));
		return builder.build();
	}

	@Override
	public Project convertEntity(ProjectTO to) {
		Project.ProjectBuilder builder = Project.builder();
		builder.id(to.getId()).code(to.getCode()).name(to.getName()).phases(to.getPhases()).build();
		Optional.ofNullable(to.isActive()).ifPresentOrElse(builder::active, () -> builder.active(true));
		Optional.ofNullable(to.getOwner()).ifPresent(owner -> builder.owner(new Owner(owner.getId(), owner.getName())));
		Optional.ofNullable(to.getManager()).ifPresent(manager -> builder.manager(new ProjectManager(manager.getId(), null)));
		return builder.build();
	}
}
