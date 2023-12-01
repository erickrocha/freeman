package com.erocha.freeman.management.usecases;

import java.util.List;
import java.util.Optional;

import com.erocha.freeman.management.domains.Team;
import com.erocha.freeman.management.gateways.TeamGateway;
import com.erocha.freeman.management.http.json.TeamParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class GetTeam {

	@Autowired
	private TeamGateway teamGateway;

	public Page<Team> execute(TeamParams params, Pageable pageable) {
		Query query = new Query();
		Optional.ofNullable(params.getName()).ifPresent(param -> query.addCriteria(Criteria.where("name").is(param)));
		Optional.ofNullable(params.getManager()).ifPresent(param -> query.addCriteria(Criteria.where("manager.id").is(param)));
		Optional.ofNullable(params.getLechLead()).ifPresent(param -> query.addCriteria(Criteria.where("techLead.id").is(param)));
		Optional.ofNullable(params.getMember()).ifPresent(param -> query.addCriteria(Criteria.where("members").in(param)));
		return teamGateway.query(query, pageable);
	}

	public List<Team> execute(Query query) {
		return teamGateway.query(query);
	}

	public List<Team> execute() {
		return teamGateway.findAll();
	}
}
