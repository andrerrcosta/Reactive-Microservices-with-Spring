package com.nobblecrafts.zwavestream.models;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
@Document
@EqualsAndHashCode
public class Wave {

    @Id
    private String id;

    @NotNull(message = "userId is null")
    @NotEmpty(message = "userId is empty")
    private String userId;

    @NotNull(message = "points is null")
    private List<WavePoint> points;

    @NotNull(message = "action is null")
    @NotEmpty(message = "action is empty")
    private String action;
    @CreatedDate
    private Date createdDate;

}
