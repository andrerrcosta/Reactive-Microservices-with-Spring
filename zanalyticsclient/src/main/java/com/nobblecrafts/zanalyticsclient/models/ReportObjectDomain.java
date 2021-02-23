package com.nobblecrafts.zanalyticsclient.models;

import java.util.LinkedList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.With;

@Getter
@Setter
@With
@ToString
@Document
@EqualsAndHashCode
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
