package com.nobblecrafts.zanalytics.utils;

import java.util.function.Function;

public abstract class ModelMapper<T, U> {

    private final Function<T, U> fromDto;
    private final Function<U, T> fromReport;

    public ModelMapper(final Function<T, U> fromDto, final Function<U, T> fromReport) {
        this.fromDto = fromDto;
        this.fromReport = fromReport;
    }

    public final U convertFromDtoToReport(final T dto) {
        return fromDto.apply(dto);
    }

    public final T convertFromReportToDTO(final U report) {
        return fromReport.apply(report);
    }
}