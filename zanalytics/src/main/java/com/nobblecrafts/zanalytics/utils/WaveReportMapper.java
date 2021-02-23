package com.nobblecrafts.zanalytics.utils;

import java.util.stream.Collectors;

import com.nobblecrafts.zanalytics.api.models.WaveReport;
import com.nobblecrafts.zanalytics.models.ReportObjectDomain;
import com.nobblecrafts.zanalytics.models.WaveReportDTO;

public class WaveReportMapper extends ModelMapper<WaveReportDTO, WaveReport> {

    public WaveReportMapper() {
        super(WaveReportMapper::convertToReport, WaveReportMapper::convertToDto);
    }

    private static WaveReportDTO convertToDto(WaveReport report) {
        return new WaveReportDTO(report.getId(), report.getUserId(), report.getState(), report.getFullWave(),
                report.getDomains().entrySet().stream()
                        .map(entryset -> new ReportObjectDomain<>(entryset.getKey(), entryset.getValue()))
                        .collect(Collectors.toList()));
    }

    private static WaveReport convertToReport(WaveReportDTO dto) {
        return new WaveReport(dto.getId(), dto.getUserId())
            .withFullWave(dto.getFullWave())
            .withState(dto.getState())
            .withDomains(dto.getDomains().stream()
                .collect(Collectors.toMap(ReportObjectDomain::getDomain, ReportObjectDomain::getValues)));

    }

}
