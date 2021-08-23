package com.beta.limited.servise;

import com.beta.limited.entity.Address;
import com.beta.limited.entity.Report;

import java.util.List;

public interface ReportService {
    void createReport(Report report);

    Report getReportById(Integer id);

    List<Report> findAll();

    void removeById(Integer id);

    void removeAll();

    void update(Report report, Address address) throws Exception;

    String getSum();

    String getLink(Integer id);

    void messageToReport(String order) throws Exception;

}
