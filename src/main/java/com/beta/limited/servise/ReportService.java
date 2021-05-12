package com.beta.limited.servise;

import com.beta.limited.entity.Report;

import java.util.List;

public interface ReportService {
    Integer createReport(Report report);

    List<Report> findAll();

    void removeAll();
    void updata(Report report);
}
