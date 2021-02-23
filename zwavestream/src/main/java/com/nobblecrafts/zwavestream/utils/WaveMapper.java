package com.nobblecrafts.zwavestream.utils;

import java.util.List;

import com.nobblecrafts.zwavestream.models.Wave;
import com.nobblecrafts.zwavestream.models.WavePoint;

import lombok.NoArgsConstructor;

/**
 * This class maps a list of WavePoint into a Wave object when its action (int z)
 * returns a number different from zero. I'm not very creative with those class names.
 */

@NoArgsConstructor
public class WaveMapper {

    public Wave toWave(List<WavePoint> points, String userId, Integer z) {
        return new Wave()
            .withUserId(userId)
            .withPoints(points)
            .withAction(z == 2 ? "Score" : "Fail");
    }

}
