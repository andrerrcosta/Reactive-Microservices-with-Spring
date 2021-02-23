package com.nobblecrafts.zanalyticsclient.models;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.With;

@Document
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
@ToString
@EqualsAndHashCode
public class WaveReportModel {
    @NotNull(message = "Wave id is null")
    private String id;
    @NotNull(message = "userId is null")
    private String userId;
    @NotNull(message = "state is null")
    private String state;
    @NotNull(message = "fullWave id is null")
    private List<Point> fullWave;
    
    private List<ReportObjectDomain<Point>> domains;
}
