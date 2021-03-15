package com.beta.limited.servise.impl;

import com.beta.limited.entity.Report;
import com.beta.limited.repository.ReportRepository;
import com.beta.limited.servise.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;

    @Override
    public List<Report> findAll() {
        List<Report> reports = new ArrayList<>();
        reportRepository.findAll().forEach(reports::add);
        return reports;
    }

    @Override
    public void removeAll() {
        reportRepository.deleteAll();
    }

    @Override
    public void updata(Report report) {
        reportRepository.save(report);
    }

    @Override
    public Integer createReport(Report report) {
        Integer id = reportRepository.save(report).getId();
        return id;
    }
}
