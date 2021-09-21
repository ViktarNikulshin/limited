package com.beta.limited.controller.api;

import com.beta.limited.entity.Address;
import com.beta.limited.entity.Report;
import com.beta.limited.entity.User;
import com.beta.limited.servise.ReportService;
import com.beta.limited.servise.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class ReportApiController {
    private final ReportService reportService;
    private final UserService userService;

    @GetMapping("/report-all")
    public List<Report> reportAll(HttpServletRequest httpServletRequest) {
//        String name = httpServletRequest.getUserPrincipal().getName();
//
//        if(httpServletRequest.isUserInRole("MANAGER")){
//            return reportService.findAllByDate();
//        }else{
//           return reportService.findAllByRunner(name);
//        }
        List<Report> list = reportService.findAllByDate();
        return  list;
    }

    @PostMapping("/report-save")
    public void save(@Valid Report report, Address address, User user) throws Exception {
        report.setAddress(address);
        User user1 = userService.getUserByLogin(user.getLogin());
        report.setRunner(user1);
        reportService.update(report, address, true);
    }

    @GetMapping("/report/{id}")
    public Report getReport(@PathVariable(value = "id", required = false) Integer id) {
        return reportService.getReportById(id);
//        List<User> users = userService.getUsersByRoleRunner();
//        Address address = report.getAddress();
//        User user = new User();
//        if(report.getRunner() == null){
//            user.setLogin("---");
//        } else {
//            user = report.getRunner();
//        }
    }

    @GetMapping("/update/{id}")
    public void updateStatusReport(@PathVariable(value = "id", required = false) Integer id) throws Exception {
        Report report = reportService.getReportById(id);
        report.setExecuted(!report.getExecuted());
        reportService.update(report, report.getAddress(), false);
    }

    @GetMapping("/report/delete/{id}")
    public void deleteReport(@PathVariable("id") Integer id) {
        reportService.removeById(id);
    }

    @GetMapping("/report")
    public String createReport() {
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
        return reportService.getLink(id, user);
    }
}
