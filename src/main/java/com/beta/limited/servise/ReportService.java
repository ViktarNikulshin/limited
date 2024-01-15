package com.beta.limited.servise;

import com.beta.limited.entity.Address;
import com.beta.limited.entity.Report;
import com.beta.limited.entity.User;
import com.beta.limited.model.ReportDto;

import java.util.List;

public interface ReportService {
    void createReport(Report report);

    Report getReportById(Integer id);

    List<Report> findAllByDate();

    List<Report> findAllByRunner(String userName);

    void removeById(Integer id);

    void removeAll();

    void removeAllByUser(String userName);

    void update(Report report, Address address, Boolean isUpdateAddress) throws Exception;

    String getSum(String login);

    String getLink(Integer id, User user);

    void messageToReport(String order, String user) throws Exception;

    void update(ReportDto reportDto);
}
