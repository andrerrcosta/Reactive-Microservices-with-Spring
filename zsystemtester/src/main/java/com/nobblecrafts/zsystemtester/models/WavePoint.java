package com.nobblecrafts.zsystemtester.models;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.With;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@With
public class WavePoint {

    @NotNull(message = "index is null")
    public int index;
    @NotNull(message = "X is null")
    public int x;
    @NotNull(message = "Y is null")
    public int y;
    @NotNull(message = "Z is null")
    public int z;
    @NotNull(message = "timestamp is null")
    public int timestamp;
}
