package com.beta.limited.controller;

import com.beta.limited.entity.Address;
import com.beta.limited.entity.Report;
import com.beta.limited.servise.AddressService;
import com.beta.limited.servise.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/reportall")
    public String reportAll(Model model) {
        model.addAttribute("reports", reportService.findAll());
        model.addAttribute("sum", reportService.getSum());
        return "reportall";
    }

    @GetMapping("/map")
    public String map(Model model) {
        List<String> points;
        points = reportService.findAll().stream().map(report -> '"' +
                report.getAddress().getCity() + " " + report.getAddress().getStreet() + " " +
                report.getAddress().getHouse() + " " +
                report.getAddress().getBuilding() + '"').collect(Collectors.toList());
        model.addAttribute("points", points);
        return "map";
    }

    @PostMapping("/reportsave")
    public String updatePostData(@Valid Report report, Address address, Model model) {
        report.setAddress(address);
        reportService.update(report, address);
        model.addAttribute("reports", reportService.findAll());
        model.addAttribute("sum", reportService.getSum());
        return "reportall";
    }

    @GetMapping("/report/{id}")
    public String updateReport(@PathVariable(value = "id", required = false) Integer id, Model model) {
        Report report = reportService.getReportById(id);
        Address address = report.getAddress();
        model.addAttribute("report", report);
        model.addAttribute("address", address);
        return "report";
    }

    @GetMapping("/report/delete/{id}")
    public String deleteReport(@PathVariable("id") Integer id, Model model) {
        reportService.removeById(id);
        model.addAttribute("reports", reportService.findAll());
        model.addAttribute("sum", reportService.getSum());
        return "reportall";
    }

    @GetMapping("/report")
    public String createReport(Model model) {
        Report report = new Report();
        Address address = new Address();
        report.setAddress(address);
        model.addAttribute("report", report);
        model.addAttribute("address", address);
        return "report";
    }
}
