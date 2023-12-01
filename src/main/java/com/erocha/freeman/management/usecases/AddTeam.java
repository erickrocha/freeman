package com.erocha.freeman.management.usecases;

import com.erocha.freeman.management.domains.Team;
import com.erocha.freeman.management.gateways.TeamGateway;
import org.springframework.stereotype.Service;

@Service
public class AddTeam {


    private TeamGateway teamGateway;

    public AddTeam(TeamGateway teamGateway) {
        this.teamGateway = teamGateway;
    }

    public Team execute(Team team) {
        return teamGateway.persist(team);
    }
}
