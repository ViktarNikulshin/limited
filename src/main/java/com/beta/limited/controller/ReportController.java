package com.beta.limited.controller;

import com.beta.limited.entity.Address;
import com.beta.limited.entity.Report;
import com.beta.limited.entity.User;
import com.beta.limited.servise.AddressService;
import com.beta.limited.servise.ReportService;
import com.beta.limited.servise.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;
    private final UserService userService;
    private final AddressService addressService;

    @GetMapping("/reportall")
    public String reportAll(Model model, HttpServletRequest httpServletRequest) {
        String name = httpServletRequest.getUserPrincipal().getName();
        if(httpServletRequest.isUserInRole("MANAGER")){
            model.addAttribute("reports", reportService.findAllByDate());
        }else{
            model.addAttribute("reports", reportService.findAllByRunner(name));
        }
        model.addAttribute("sum", reportService.getSum(httpServletRequest.getUserPrincipal().getName()));
        return "reportall";
    }

    @GetMapping("/map")
    public String map(Model model, HttpServletRequest httpServletRequest) {
        List<String> points;
        points = reportService.findAllByDate().stream().map(report -> report.getAddress().getLat()
                + " " + report.getAddress().getLon()).collect(Collectors.toList());
        model.addAttribute("points", points);
        return "yandex-map";
    }

    @PostMapping("/reportsave")
    public String updatePostData(@Valid Report report, Address address, User user, Model model, HttpServletRequest httpServletRequest) throws Exception {
        report.setAddress(address);
        User user1 = userService.getUserByLogin(user.getLogin());
        report.setRunner(user1);
        reportService.update(report, address, true);
        return "redirect:/reportall";
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
        reportService.update(report, report.getAddress(), false);
        return "redirect:/reportall";
    }

    @PostMapping("/update")
    public String updateStatusReport(@Valid Report report, Address address, User user, HttpServletRequest httpServletRequest) throws Exception {
        Report report1 = reportService.getReportById(report.getId());
        report1.setExecuted(report.getExecuted());
        Address address1 =addressService.findAddressById(address.getId());
        address1.setRouting(address.getRouting());
        reportService.update(report1, address1, false);
        return "redirect:/reportall";
    }

    @GetMapping("/report/delete/{id}")
    public String deleteReport(@PathVariable("id") Integer id, Model model, HttpServletRequest httpServletRequest) {
        reportService.removeById(id);
        return "redirect:/reportall";
    }

    @GetMapping("/report")
    public String createReport(Model model) {
        Report report = new Report();
        Address address = new Address();
        report.setAddress(address);
        List<User> users = userService.getUsersByRoleRunner();
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

    @GetMapping("/report/delete-all")
    public String deleteAllReport(Model model, HttpServletRequest httpServletRequest){
        reportService.removeAllByUser(httpServletRequest.getUserPrincipal().getName());
        return "redirect:/reportall";
    }

    @GetMapping("/map/{id}")
    public String map(@PathVariable("id") Integer id, Model model, HttpServletRequest httpServletRequest){
        User user = userService.getUserByLogin(httpServletRequest.getUserPrincipal().getName());
        model.addAttribute("link",reportService.getLink(id, user));
        return "map";
    }

    @GetMapping("report/build-routing/{id}")
    public String buildRouting(@PathVariable("id") Integer id, HttpServletRequest httpServletRequest){
        String name = httpServletRequest.getUserPrincipal().getName();
        List<Address> addresses = reportService.findAllByRunner(name).stream().map(Report::getAddress).collect(Collectors.toList());
        addressService.findOptimalRouting(addresses, id);
        return "redirect:/reportall";
    }

    @PostMapping("report/update-all")
    public String buildRouting(@Valid List<Report> reports, HttpServletRequest httpServletRequest){
        String name = httpServletRequest.getUserPrincipal().getName();
        List<Address> addresses = reportService.findAllByRunner(name).stream().map(Report::getAddress).collect(Collectors.toList());

        return "redirect:/reportall";
    }
}
