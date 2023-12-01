package com.erocha.freeman.management.http.mapper;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.erocha.freeman.commons.mapper.Mapper;
import com.erocha.freeman.management.domains.Member;
import com.erocha.freeman.management.domains.Team;
import com.erocha.freeman.management.http.json.MemberTO;
import com.erocha.freeman.management.http.json.TeamTO;

@Component
public class TeamMapper implements Mapper<Team, TeamTO> {

	@Override
	public TeamTO convertTransferObject(Team team) {
		TeamTO.TeamTOBuilder builder = TeamTO.builder();
		builder.id(team.getId()).name(team.getName());
		Optional.ofNullable(team.getManager()).ifPresent(manager -> builder.manager(getMemberMapper().convertTransferObject(manager)).build());
		getTechLead(team.getMembers())
				.ifPresent(techLead -> builder.techLead(MemberTO.builder().id(techLead.getId()).name(techLead.getName()).lead(true).build()));
		builder.members(getMemberMapper().convertTransferObject(team.getMembers()));
		return builder.build();
	}

	private Optional<Member> getTechLead(List<Member> members) {
		return members.stream().filter(Member::isLead).findFirst();
	}

	@Override
	public Team convertEntity(TeamTO to) {
		Team.TeamBuilder builder = Team.builder();
		builder.id(to.getId()).name(to.getName());
		Optional.ofNullable(to.getManager()).ifPresent(manager -> builder.manager(getMemberMapper().convertEntity(manager)).build());
		Optional.ofNullable(to.getTechLead()).ifPresent(techLead -> builder.member(new Member(techLead.getId(), techLead.getName(), true)).build());
		builder.members(getMemberMapper().convertEntity(to.getMembers()));
		return builder.build();
	}

	private Mapper<Member, MemberTO> getMemberMapper() {
		return new Mapper<>() {
			@Override
			public MemberTO convertTransferObject(Member member) {
				return MemberTO.builder().id(member.getId()).name(member.getName()).build();
			}

			@Override
			public Member convertEntity(MemberTO to) {
				return new Member(to.getId(), to.getName(), false);
			}
		};
	}
}
