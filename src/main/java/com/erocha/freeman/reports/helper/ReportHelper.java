package com.erocha.freeman.reports.helper;

import com.erocha.freeman.commons.utils.DateTools;
import com.erocha.freeman.management.domains.Project;
import com.erocha.freeman.reports.http.json.ProjectAmount;
import com.erocha.freeman.reports.http.json.ReportTO;
import com.erocha.freeman.reports.http.json.TimeAmount;
import com.erocha.freeman.time.domains.Appointment;
import com.erocha.freeman.time.domains.Status;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ReportHelper {

    public ReportTO builder(List<Appointment> appointments, LocalDate startDate, LocalDate endDate) {
        ReportTO report = ReportTO.builder().startDate(startDate).endDate(endDate).build();
        Long approvedMinutes = appointments.stream().filter(appointment -> appointment.getStatus().equals(Status.APPROVED))
                .mapToLong(Appointment::getAmountInMinutes).sum();
        Long unapprovedMinutes = appointments.stream().filter(appointment -> appointment.getStatus().equals(Status.SAVED))
                .mapToLong(Appointment::getAmountInMinutes).sum();
        report.setDailyAmount(getCalendarAmounts(startDate, endDate, appointments));
        report.setProjectsAmounts(getProjectAmounts(appointments));
        report.setHour(approvedMinutes / 60);
        report.setMinute(approvedMinutes % 60);
        report.setTotalApprovedHour(approvedMinutes / 60);
        report.setTotalApprovedMinutes(approvedMinutes % 60);
        report.setTotalUnapprovedHour(unapprovedMinutes / 60);
        report.setTotalUnapprovedMinutes(unapprovedMinutes % 60);
        return report;

    }

    private List<ProjectAmount> getProjectAmounts(List<Appointment> appointments) {
        Set<Project> projects = appointments.stream().map(Appointment::getProject).collect(Collectors.toSet());
        List<ProjectAmount> projectAmounts = new ArrayList<>();
        projects.forEach(project -> {
            Integer projectAmount = appointments.stream().filter(appointment -> appointment.getProject().getId().equals(project.getId()))
                    .mapToInt(Appointment::getAmountInMinutes).sum();
            projectAmounts.add(new ProjectAmount(project, projectAmount));
        });
        return projectAmounts;
    }

    private List<TimeAmount> getCalendarAmounts(LocalDate startDate, LocalDate endDate, List<Appointment> appointments) {
        LocalDate firstDate = DateTools.findFirstDayOfWeek(startDate, DayOfWeek.SUNDAY);
        LocalDate lastDate = DateTools.findLastDayOfWeek(endDate, DayOfWeek.SATURDAY);
        List<LocalDate> localDates = List.of(firstDate);
        while (firstDate.isBefore(lastDate)) {
            firstDate = firstDate.plusDays(1);
            localDates.add(firstDate);
        }
        List<TimeAmount> dailyAmounts = new ArrayList<>();
        localDates.stream().forEach(date -> {
            Integer dayAmount = appointments.stream().filter(appointment -> appointment.getDate().equals(date)).mapToInt(Appointment::getAmountInMinutes).sum();
            dailyAmounts.add(new TimeAmount(date, dayAmount));
        });
        return dailyAmounts;
    }
}
