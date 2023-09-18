package com.example.G4_DailyReport.controller;

import javax.validation.Valid;
import com.example.G4_DailyReport.model.Report;
import com.example.G4_DailyReport.repository.ReportRepository;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("/reports")
@SessionAttributes("report")
public class ReportController {

    private ReportRepository reportRepo;
    public ReportController(ReportRepository reportRepo){
        this.reportRepo = reportRepo;
    }
    @GetMapping("/new")
    public String reportForm(){
        return "new_report";
    }

    @PostMapping
    public String processReport(@Valid Report report, Errors errors, SessionStatus sessionStatus){

        if(errors.hasErrors()){
            return "new_report";
        }

        reportRepo.save(report);
        sessionStatus.setComplete();
        return "redirect:/";
    }
}
