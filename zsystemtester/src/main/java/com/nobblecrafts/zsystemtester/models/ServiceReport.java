package com.nobblecrafts.zsystemtester.models;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class ServiceReport implements TestReport {

    private Long timeBetweenAMQPExchange;
    private Long averagePerRequestProcessing;
    private List<ResourceConsumption> resourceConsumption;
    private String message;
    
}
