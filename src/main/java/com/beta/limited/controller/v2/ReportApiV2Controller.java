package com.beta.limited.controller.v2;


import com.beta.limited.entity.Report;
import com.beta.limited.mapper.ReportMapper;
import com.beta.limited.model.ReportDto;
import com.beta.limited.servise.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v2/report")
@RequiredArgsConstructor
public class ReportApiV2Controller {

    private final ReportMapper reportMapper;
    private final ReportService reportService;

    @GetMapping
    public List<ReportDto> getAllReport() {
        return reportMapper.domainListToDtoList(reportService.findAllByDate()
                .stream()
                .sorted(Comparator.comparing(o -> o.getAddress().getRouting()))
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ReportDto getReportById(@PathVariable("id") Integer id) {
        return reportMapper.domainToDto(reportService.getReportById(id));
    }

    @PostMapping
    public void createReport(@RequestBody ReportDto reportDto) {
        reportService.createReport(reportMapper.dtoToDomain(reportDto));
    }

    @PostMapping("/update")
    public void updateReport(@RequestBody ReportDto reportDto) {
        reportService.update(reportDto);
    }

    @GetMapping("/delete/{id}")
    public void deleteReportById(@PathVariable("id") Integer id) {
        reportService.removeById(id);
    }

}
