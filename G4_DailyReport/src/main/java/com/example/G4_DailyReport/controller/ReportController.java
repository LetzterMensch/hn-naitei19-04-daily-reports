package com.example.G4_DailyReport.controller;

import javax.validation.Valid;

import com.example.G4_DailyReport.model.Department;
import com.example.G4_DailyReport.model.Project;
import com.example.G4_DailyReport.model.Report;
import com.example.G4_DailyReport.model.User;
import com.example.G4_DailyReport.repository.ProjectRepository;
import com.example.G4_DailyReport.repository.ReportRepository;
import com.example.G4_DailyReport.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/reports")
@SessionAttributes("report")
public class ReportController {
        @ModelAttribute(name = "report")
    public Report report() {
        Report report = new Report();
        report.setReportDate(LocalDate.now());
        return report;
    }
    @Autowired
    private ReportRepository reportRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private ProjectRepository projectRepo;

    public ReportController(ReportRepository reportRepo) {
        this.reportRepo = reportRepo;
    }
    @GetMapping("/new")
    public String reportForm(Model model) {
        //Redirect if today's report already exists
        Report report = reportRepo.findReportByReportDate(LocalDate.now());
        if (report != null) {
            model.addAttribute(report);
            return "/user/reports/edit";
        } else {
            report = new Report();
            report.setReportDate(LocalDate.now());
            List<Project> projects = projectRepo.findAll();
            model.addAttribute(report);
            model.addAttribute("projects",projects);
            return "/user/reports/new";
        }
    }

    @GetMapping("/edit")
    public String editReportForm(@ModelAttribute Report report, Model model) {
        model.addAttribute("report", report);
        return "/user/reports/edit";
    }

    @PostMapping("/new")
    public String processReport(@Valid Report report, Errors errors) {
        List<User> users = userRepo.findAll();
//        List<Project> projects = projectRepo.findAll();
//        report.setProject(projects.get(0));
        report.setUser(users.get(0));
        if (errors.hasErrors()) {
            return "/user/reports/new";
        }
        reportRepo.save(report);
        return "redirect:/reports/edit";
    }
}
