package com.nobblecrafts.zanalytics.models;

import java.util.LinkedList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.With;

@Getter
@Setter
@With
@ToString
public class ReportObjectDomain<T> {
    private T domain;
    private List<Integer> values;

    public ReportObjectDomain() {
        values = new LinkedList<>();
    }

    public ReportObjectDomain(T domain, List<Integer> values) {
        this.domain = domain;
        this.values = values;
    }
}
