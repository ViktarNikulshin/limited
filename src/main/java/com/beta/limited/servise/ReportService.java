package com.beta.limited.servise;

import com.beta.limited.entity.Address;
import com.beta.limited.entity.Report;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface ReportService {
    Integer createReport(Report report);

    Report getReportById(Integer id);

    List<Report> findAll();

    void removeById(Integer id);

    void removeAll();

    void update(Report report, Address address);

    Double getSum();
}
