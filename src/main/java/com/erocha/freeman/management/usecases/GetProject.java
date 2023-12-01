package com.erocha.freeman.management.usecases;

import java.util.List;
import java.util.Optional;

import com.erocha.freeman.management.domains.Project;
import com.erocha.freeman.management.gateways.ProjectGateway;
import com.erocha.freeman.management.http.json.ProjectParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class GetProject {

	private ProjectGateway gateway;

	public GetProject(ProjectGateway gateway) {
		this.gateway = gateway;
	}

	public Optional<Project> execute(String id) {
		return gateway.findById(id);
	}

	public Page<Project> execute(ProjectParams params, Pageable pageable) {
		Query query = new Query();
		Optional.ofNullable(params.getName()).ifPresent(param -> query.addCriteria(Criteria.where("name").is(param)));
		Optional.ofNullable(params.getResponsible()).ifPresent(param -> query.addCriteria(Criteria.where("responsible").is(param)));
		Optional.ofNullable(params.getClient()).ifPresent(param -> query.addCriteria(Criteria.where("client").is(param)));
		Optional.ofNullable(params.getPhase()).ifPresent(param -> query.addCriteria(Criteria.where("phases").in(param)));
		return gateway.query(query, pageable);
	}

	public List<Project> execute() {
		return gateway.findAll();
	}
}
