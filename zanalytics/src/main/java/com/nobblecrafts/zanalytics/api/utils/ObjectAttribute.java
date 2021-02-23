package com.nobblecrafts.zanalytics.api.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.With;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@With
@ToString
public class ObjectAttribute<T, U> {

    private T key;
    private U attribute;

}
