package com.beta.limited.controller;

import com.beta.limited.entity.Report;
import com.beta.limited.servise.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/reportall")
    public String reportAll(Model model){
    model.addAttribute("reports", reportService.findAll());
    return "reportall";
    }

    @PostMapping("/report/create")
    public String create(@ModelAttribute(value = "report")Report report, Model model){
    return "reportall";
    };

    @GetMapping("/map")
    public String map(Model model){
        List<String> points = new ArrayList<>();
        points = reportService.findAll().stream().map(report -> '"' + report.getAddress()+ '"').collect(Collectors.toList());
        model.addAttribute("points", points);
        model.addAttribute("street", "Минск, Есенина 6");
        return "map";
    }

    @GetMapping("/reportsave")
    public  String updateData(@RequestParam("id") Integer id,
                              @RequestParam("address")String address, Model model){
        model.addAttribute("reports",reportService.findAll());
        return "reportall";
    }

    @PostMapping("/reportsave")
    public  String updatePostData(@ModelAttribute Report report, Model model){
        reportService.updata(report);
        model.addAttribute("reports",reportService.findAll());
        return "reportall";
    }
}
