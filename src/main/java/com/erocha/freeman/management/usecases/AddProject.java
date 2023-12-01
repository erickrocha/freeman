package com.erocha.freeman.management.usecases;

import com.erocha.freeman.commons.exceptions.ResourceNotFoundException;
import com.erocha.freeman.management.domains.Project;
import com.erocha.freeman.management.gateways.ProjectGateway;
import com.erocha.freeman.security.domains.User;
import com.erocha.freeman.security.gateways.UserGateway;
import org.springframework.stereotype.Service;

@Service
public class AddProject {


    private ProjectGateway gateway;


    private UserGateway userGateway;

    public AddProject(ProjectGateway gateway, UserGateway userGateway) {
        this.gateway = gateway;
        this.userGateway = userGateway;
    }

    public Project execute(Project project) {
        User manager = userGateway.findById(project.getManager().getId()).orElseThrow(ResourceNotFoundException::new);
        project.getManager().setName(manager.getName());
        return gateway.persist(project);
    }
}
