package com.beta.limited.mapper;


import com.beta.limited.entity.Report;
import com.beta.limited.model.ReportDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReportMapper {
        Report dtoToDomain (ReportDto reportDto);
        ReportDto domainToDto (Report report);
        List<ReportDto> domainListToDtoList (List<Report> reports);
}
