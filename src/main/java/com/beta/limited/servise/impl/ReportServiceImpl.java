package com.beta.limited.servise.impl;

import com.beta.limited.entity.Address;
import com.beta.limited.entity.Report;
import com.beta.limited.repository.ReportRepository;
import com.beta.limited.servise.AddressService;
import com.beta.limited.servise.ReportService;
import lombok.RequiredArgsConstructor;
import org.glassfish.jersey.internal.guava.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;
    private final AddressService addressService;

    @Override
    @Transactional
    public List<Report> findAll() {
        List<Report> reports = new ArrayList<>();
        reportRepository.findAll().forEach(reports::add);
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
    @Transactional
    public void update(Report report, Address address) {
        address.setReport(report);
        report.setAddress(addressService.createAddress(address));
        reportRepository.save(report);
    }

    @Override
    public Double getSum() {
        List<Report> reports = Lists.newArrayList(reportRepository.findAll());
        Double sum =reports.stream().filter(r -> r.getPrise() != null).mapToDouble(r -> r.getPrise()).sum();
        reports.stream().filter(r -> r.getPrise() != null).mapToDouble(r -> r.getPrise()).sum();
        return sum;
    }

    @Override
    public Integer createReport(Report report) {
        Address address = new Address();
        report.setAddress(address);
        Integer id = reportRepository.save(report).getId();
        address.setReport(report);
        addressService.createAddress(address);
        return id;
    }

    @Override
    @Transactional
    public Report getReportById(Integer id) {
        return reportRepository.findById(id).orElse(null);
    }
}
