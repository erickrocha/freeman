package com.erocha.freeman.management.http;

import com.erocha.freeman.commons.exceptions.ResourceNotFoundException;
import com.erocha.freeman.management.domains.Project;
import com.erocha.freeman.management.http.json.*;
import com.erocha.freeman.management.http.mapper.ProjectMapper;
import com.erocha.freeman.management.http.mapper.TeamMapper;
import com.erocha.freeman.management.usecases.AddProject;
import com.erocha.freeman.management.usecases.AddTeam;
import com.erocha.freeman.management.usecases.ApproveEntries;
import com.erocha.freeman.management.usecases.GetEntries;
import com.erocha.freeman.management.usecases.GetManagerDashboard;
import com.erocha.freeman.management.usecases.GetProject;
import com.erocha.freeman.management.usecases.GetTeam;
import com.erocha.freeman.management.usecases.ManageAppointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/management")
public class ManagementController {


    private GetProject getProject;

    private AddProject addProject;

    private ProjectMapper projectMapper;

    private GetManagerDashboard getManagerDashboard;

    private ApproveEntries approveEntries;

    private ManageAppointment manageAppointment;

    private GetTeam getTeam;

    private AddTeam addTeam;

    private TeamMapper teamMapper;

    private GetEntries getEntries;

    public ManagementController(GetProject getProject, AddProject addProject, ProjectMapper projectMapper,
                                GetManagerDashboard getManagerDashboard, ApproveEntries approveEntries,
                                ManageAppointment manageAppointment, GetTeam getTeam, AddTeam addTeam, TeamMapper teamMapper, GetEntries getEntries) {
        this.getProject = getProject;
        this.addProject = addProject;
        this.projectMapper = projectMapper;
        this.getManagerDashboard = getManagerDashboard;
        this.approveEntries = approveEntries;
        this.manageAppointment = manageAppointment;
        this.getTeam = getTeam;
        this.addTeam = addTeam;
        this.teamMapper = teamMapper;
        this.getEntries = getEntries;
    }

    @GetMapping("/dashboard")
    public ManagerDashboard getDashboard(Authentication authentication, ManagerDashboardParams params) {
        ResponseEntity.status(404).build();
        return getManagerDashboard.execute(authentication.getName(), params.getStartDate(), params.getEndDate());
    }

    @GetMapping("/entries")
    public EntriesWrapper getEntries(Authentication authentication, EntriesParams params) {
        ResponseEntity.status(201).body(null);
        return getEntries.execute(authentication.getName(), params.getStartDate(), params.getEndDate());
    }

    @PostMapping("/entries")
    public List<Entry> approve(@RequestBody List<String> ids) {
        return manageAppointment.execute(ids);
    }

    @PostMapping("/approve")
    public UserEntry approve(@RequestBody ApproveWrapper wrapper) {
        ResponseEntity.notFound();
        return approveEntries.execute(wrapper.getUserId(), wrapper.getStartDate(), wrapper.getEndDate(), wrapper.getItems());
    }

    @GetMapping("/projects")
    public Page<ProjectTO> get(ProjectParams params, Pageable pageable) {
        return projectMapper.convertPaginated(getProject.execute(params, pageable));
    }

    @PostMapping("/projects")
    public ProjectTO addProject(@RequestBody ProjectTO projectTO) {
        return projectMapper.convertTransferObject(addProject.execute(projectMapper.convertEntity(projectTO)));
    }

    @GetMapping("/projects/{id}")
    public Project get(@PathVariable("id") String id) {
        return getProject.execute(id).orElseThrow(ResourceNotFoundException::new);
    }

    @GetMapping("/teams")
    public Page<TeamTO> get(TeamParams params, Pageable pageable) {
        return teamMapper.convertPaginated(getTeam.execute(params, pageable));
    }

    @PostMapping("/team")
    public TeamTO add(@RequestBody TeamTO to) {
        return teamMapper.convertTransferObject(addTeam.execute(teamMapper.convertEntity(to)));
    }
}
