package com.beta.limited.controller;

import com.beta.limited.entity.Address;
import com.beta.limited.entity.Report;
import com.beta.limited.entity.User;
import com.beta.limited.servise.ReportService;
import com.beta.limited.servise.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;
    private final UserService userService;

    @GetMapping("/reportall")
    public String reportAll(Model model, HttpServletRequest httpServletRequest) {
        String name = httpServletRequest.getUserPrincipal().getName();
        model.addAttribute("reports", reportService.findAllByRunner(name));
        model.addAttribute("sum", reportService.getSum(httpServletRequest.getUserPrincipal().getName()));
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
    public String updatePostData(@Valid Report report, Address address, User user, Model model, HttpServletRequest httpServletRequest) throws Exception {
        report.setAddress(address);
        User user1 = userService.getUserByLogin(user.getLogin());
        report.setRunner(user1);
        reportService.update(report, address);
        model.addAttribute("reports", reportService.findAllByRunner(httpServletRequest.getUserPrincipal().getName()));
        model.addAttribute("sum", reportService.getSum(httpServletRequest.getUserPrincipal().getName()));
        return "reportall";
    }

    @GetMapping("/report/{id}")
    public String updateReport(@PathVariable(value = "id", required = false) Integer id, Model model) {
        Report report = reportService.getReportById(id);
        List<User> users = userService.getUsersByRoleRunner();
        Address address = report.getAddress();
        User user = new User();
        if(report.getRunner() == null){
            user.setLogin("---");
        } else {
            user = report.getRunner();
        }
        model.addAttribute("user", user);
        model.addAttribute("users", users);
        model.addAttribute("report", report);
        model.addAttribute("address", address);
        return "report";
    }

    @GetMapping("/update/{id}")
    public String updateStatusReport(@PathVariable(value = "id", required = false) Integer id, Model model, HttpServletRequest httpServletRequest) throws Exception {
        Report report = reportService.getReportById(id);
        report.setExecuted(!report.getExecuted());
        reportService.update(report, report.getAddress());
        model.addAttribute("reports", reportService.findAllByRunner(httpServletRequest.getUserPrincipal().getName()));
        model.addAttribute("sum", reportService.getSum(httpServletRequest.getUserPrincipal().getName()));
        return "reportall";
    }

    @GetMapping("/report/delete/{id}")
    public String deleteReport(@PathVariable("id") Integer id, Model model, HttpServletRequest httpServletRequest) {
        reportService.removeById(id);
        model.addAttribute("reports", reportService.findAllByRunner(httpServletRequest.getUserPrincipal().getName()));
        model.addAttribute("sum", reportService.getSum(httpServletRequest.getUserPrincipal().getName()));
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

    @GetMapping("/report/delete-all")
    public String deleteAllReport(Model model, HttpServletRequest httpServletRequest){
        reportService.removeAllByUser(httpServletRequest.getUserPrincipal().getName());
        model.addAttribute("reports", reportService.findAllByRunner(httpServletRequest.getUserPrincipal().getName()));
        model.addAttribute("sum", reportService.getSum(httpServletRequest.getUserPrincipal().getName()));
        return "reportall";
    }

    @GetMapping("/map/{id}")
    public String map(@PathVariable("id") Integer id, Model model){
        model.addAttribute("link",reportService.getLink(id));
        return "map";
    }
}
