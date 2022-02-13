package com.beta.limited.controller;

import com.beta.limited.entity.Report;
import com.beta.limited.servise.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ModalDialogsController {
    public final ReportService reportService;
    @GetMapping(value="/update-point/{id}")
    public String getStudentModal(@PathVariable("id") Integer id, ModelMap model) {
        Report report = reportService.getReportById(id);
        model.addAttribute("report", report);
        model.addAttribute("address", report.getAddress());
        return "modal/reportModal.html :: report";
    }
}
