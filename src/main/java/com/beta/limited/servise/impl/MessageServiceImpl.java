package com.beta.limited.servise.impl;

import com.beta.limited.entity.Report;
import com.beta.limited.repository.ReportRepository;
import com.beta.limited.servise.MessageService;
import com.beta.limited.servise.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final ReportService reportService;

    @Override
    public void messageToReport(String message) {
        List<String> list = Arrays.asList(message.split("\n"));
        System.out.println(list.toString());
        List<String> notEmptyList = list.stream().filter(str -> str.length() > 1).collect(Collectors.toList());
        //reportService.removeAll();
        StringBuilder strReport = new StringBuilder();
        for (int i = 0; i < notEmptyList.size(); i++) {
            strReport.append(notEmptyList.get(i));
            if (notEmptyList.get(i).contains("+375")) {
              Report report = new Report();
                int index = notEmptyList.get(i).indexOf("+375");
                report.setOrder(strReport.toString());
                report.setPhoneNumber(notEmptyList.get(i).substring(index, index + 13));
                reportService.createReport(report);
                System.out.println("Phone :  " + report.getPhoneNumber());
                strReport = new StringBuilder();
            }
        }
    }
}

