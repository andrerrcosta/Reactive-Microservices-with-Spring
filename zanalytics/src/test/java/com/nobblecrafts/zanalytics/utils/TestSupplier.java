package com.nobblecrafts.zanalytics.utils;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.nobblecrafts.zanalytics.api.models.Point;
import com.nobblecrafts.zanalytics.api.models.WaveStream;

public class TestSupplier {

    public static WaveStream createRandomWave(int length) {
        return WaveStream.builder()
            .userId(UUID.randomUUID().toString())
            .points(TestSupplier.createRandomPointList(length))
            .build();
    }

    public static WaveStream createLinearWave(int length) {
        return WaveStream.builder()
            .userId(UUID.randomUUID().toString())
            .points(TestSupplier.createLinearPointList(length))
            .build();
    }

    private static List<Point> createRandomPointList(int length) {
        List<Point> output = new LinkedList<>();
        var random = new Random();
        for(int i = 0; i < length; i++) {
            output.add(new Point(i, i, random.nextInt(1000), random.nextInt(1000)));
        }
        return output;
    }

    private static List<Point> createLinearPointList(int length) {
        List<Point> output = new LinkedList<>();
        var random = new Random();
        var first = new Point(0, 0, random.nextInt(1000), random.nextInt(1000));
        var m = random.nextInt(10) - 5;
        output.add(first);
        for(int i = 1; i < length; i++) {
            output.add(new Point(i, i, first.getX() + (m * i), first.getY() + (m * i)));
        }
        return output;
    }
}
