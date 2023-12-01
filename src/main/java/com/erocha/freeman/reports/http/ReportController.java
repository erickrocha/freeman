package com.erocha.freeman.reports.http;

import com.erocha.freeman.reports.http.json.ProjectReportWrapper;
import com.erocha.freeman.reports.http.json.ReportParams;
import com.erocha.freeman.reports.http.json.TimeReportWrapper;
import com.erocha.freeman.reports.usecases.GetProjectReport;
import com.erocha.freeman.reports.usecases.GetTimeReport;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
public class ReportController {


    private GetTimeReport getTimeReport;

    private GetProjectReport getProjectReport;

    public ReportController(GetTimeReport getTimeReport, GetProjectReport getProjectReport) {
        this.getTimeReport = getTimeReport;
        this.getProjectReport = getProjectReport;
    }

    @GetMapping("/time")
    public TimeReportWrapper getTimeDetail(Authentication authentication, ReportParams params) {
        return getTimeReport.execute(authentication.getName(), params.getStartDate(), params.getEndDate(), params.getProject(), params.getPhase());
    }

    @GetMapping("/projects")
    public ProjectReportWrapper getProjectDetail(Authentication authentication, ReportParams params) {
        String username = authentication.getName();
        return getProjectReport.execute(params.getProject(), params.getStartDate(), params.getEndDate(), username);
    }
}
