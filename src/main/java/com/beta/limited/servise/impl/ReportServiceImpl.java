package com.beta.limited.servise.impl;

import com.beta.limited.entity.Address;
import com.beta.limited.entity.Reference;
import com.beta.limited.entity.Report;
import com.beta.limited.entity.User;
import com.beta.limited.repository.ReportRepository;
import com.beta.limited.servise.AddressService;
import com.beta.limited.servise.ReferenceService;
import com.beta.limited.servise.ReportService;
import com.beta.limited.servise.UserService;
import lombok.RequiredArgsConstructor;
import org.glassfish.jersey.internal.guava.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.soap.SOAPBinding;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;
    private final AddressService addressService;
    private final ReferenceService referenceService;
    private final UserService userService;

    @Override
    @Transactional
    public List<Report> findAll() {
        List<Report> reports = new ArrayList<>();
        reportRepository.findAll().forEach(reports::add);
        reports = reports.stream().sorted(Comparator.comparing(Report::getExecuted)).collect(Collectors.toList());
        return reports;
    }

    @Override
    public List<Report> findAllByRunner(String userName) {
        User user = userService.getUserByLogin(userName);
        List<Report> reports = new ArrayList<>();
        reportRepository.findAll().forEach(reports::add);
        reports = reports.stream().filter(report -> report.getRunner() == user).collect(Collectors.toList());
        return reports;
    }

    @Override
    public void removeById(Integer id) {
        reportRepository.deleteById(id);
    }

    @Override
    public void removeAll() {
        reportRepository.deleteAll();
    }

    @Override
    public void removeAllByUser(String userName) {
        User user = userService.getUserByLogin(userName);
        List<Report> reports = new ArrayList<>();
        reportRepository.findAll().forEach(reports::add);
        reports = reports.stream().filter(report -> report.getRunner() == user).collect(Collectors.toList());
        for (Report report : reports) {
            reportRepository.delete(report);
        }
    }

    @Override
    @Transactional
    public void update(Report report, Address address) throws Exception {
        address.setReport(report);
        if(report.getExecuted() == null){
            report.setExecuted(false);
        }
        report.setAddress(addressService.findAddressToReport(address.getFullAddress(), address));
        reportRepository.save(report);
    }

    @Override
    public String getSum(String login) {
        User user = userService.getUserByLogin(login);
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        List<Report> reports = Lists.newArrayList(reportRepository.findAll());
        Double sum =reports.stream().filter(r -> r.getPrise() != null && r.getRunner() == user).mapToDouble(r -> r.getPrise()).sum();
        reports.stream().filter(r -> r.getPrise() != null).mapToDouble(r -> r.getPrise()).sum();
        return df.format(sum);
    }

    @Override
    public String getLink(Integer id) {
        StringBuilder baseUrl;
        if(id == 2){
             baseUrl = new StringBuilder("http://gov.swizz.ru/route/%7B%22start%22:%5B53.934721,27.427883%5D,%22end%22:%5B53.863038,27.460853%5D,%22points%22:%5B");
        }else {
             baseUrl = new StringBuilder("http://gov.swizz.ru/route/%7B%22start%22:%5B53.934721,27.427883%5D,%22end%22:%5B53.940547,27.605534%5D,%22points%22:%5B");
        }
        List<Report> reports = Lists.newArrayList(reportRepository.findAll());
        List<Report> notNullPoint = reports.stream().filter(r -> r.getAddress().getPos() != null).collect(Collectors.toList());
        for (Report report : notNullPoint){
            baseUrl.append("%5B");
            baseUrl.append(report.getAddress().getPos());
            baseUrl.append("%5D,");
        }
        baseUrl.deleteCharAt(baseUrl.length()-1);
        baseUrl.append("%5D,%22method%22:%221%22,%22optimization%22:%223%22,%22center%22:%5B53.87957799997894,27.687190999999995%5D,%22zoom%22:12%7D");
        System.out.println(baseUrl.toString());
        return baseUrl.toString();
    }

    @Override
    public void messageToReport(String order, String user) throws Exception {
        Report report = new Report();
        report.setOrder(order);
        report.setPhoneNumber(getPhone(order));
        report.setAddress(addressService.findAddressToReport(order, new Address()));
        report.setPrise(getPrice(order));
        report.setExecuted(false);
        report.setRunner(getUserByTelegramName(user));
        createReport(report);
    }

    private String getPhone(String order) {
        if (order.contains("+375")) {
            int index = order.indexOf("+375");
            StringBuilder phone = new StringBuilder(order.substring(index).
                    replaceAll(" ","").
                    replaceAll("-",""));
            System.out.println("Phone :  " + phone.substring(0, 13));
            return phone.substring(0, 13);
        }
        List<String> codes = referenceService.getAllReferenceByType(4).stream().map(Reference::getName).collect(Collectors.toList());
        for (String code : codes) {
            if (order.contains(code)){
                if(order.charAt(order.indexOf(code)-1) == ' '){
                    StringBuilder phone = new StringBuilder(order.substring(order.indexOf(code)).
                            replaceFirst(code.substring(0,2),"+375").
                            replaceAll(" ","").
                            replaceAll("-", ""));
                    return phone.substring(0, 13);
                }

            }
        }
        return "не найден";
    }

    private User getUserByTelegramName(String name){
        return userService.getUserByTelegramName(name);
    }

    @Override
    public void createReport(Report report) {
        report.getAddress().setReport(report);
        Integer id = reportRepository.save(report).getId();
        report.getAddress().setId(id);
    }

    @Override
    @Transactional
    public Report getReportById(Integer id) {
        return reportRepository.findById(id).orElse(null);
    }

    private Double getPrice(String order){
        Double price = 0.0;
        if(order.contains("оплач") || order.contains("pay")) return price;
        Pattern p = Pattern.compile("([0-9]+([,][0-9]*)?|[,][0-9]+)");
        Matcher m = p.matcher(order);
        int i = 0;
        while (m.find(i)) {
            int j = m.end();
            if(order.startsWith(" р ", j) || order.startsWith(" р.", j) || order.startsWith("р ",j)){
                price += Double.parseDouble(m.group().replace(',','.'));
            }
            i = m.end();
        }
        return price;
    }
}
