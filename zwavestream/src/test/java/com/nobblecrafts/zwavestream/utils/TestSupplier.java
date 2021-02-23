package com.nobblecrafts.zwavestream.utils;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.nobblecrafts.zwavestream.models.Wave;
import com.nobblecrafts.zwavestream.models.WavePoint;

public class TestSupplier {

    public static Wave createValidWave(int length) {
        return Wave.builder()
            .userId(UUID.randomUUID().toString())
            .action("test")
            .points(TestSupplier.createRandomPointList(length))
            .build();
    }

    public static Wave createInvalidWave(int length) {
        return Wave.builder()
            .action("test")
            .points(TestSupplier.createRandomPointList(length))
            .build();
    }

    public static Wave createRandomWave(int length) {
        return Wave.builder()
            .userId(UUID.randomUUID().toString())
            .points(TestSupplier.createRandomPointList(length))
            .build();
    }

    public static Wave createLinearWave(int length) {
        return Wave.builder()
            .userId(UUID.randomUUID().toString())
            .points(TestSupplier.createLinearPointList(length))
            .build();
    }

    private static List<WavePoint> createRandomPointList(int length) {
        List<WavePoint> output = new LinkedList<>();
        var random = new Random();
        for(int i = 0; i < length; i++) {
            if(i == length - 1)
            output.add(new WavePoint(i, random.nextInt(1000), random.nextInt(1000), 2, i));
            else
            output.add(new WavePoint(i, random.nextInt(1000), random.nextInt(1000), 0, i));
        }
        return output;
    }

    private static List<WavePoint> createLinearPointList(int length) {
        List<WavePoint> output = new LinkedList<>();
        var random = new Random();
        var first = new WavePoint(0, random.nextInt(1000), random.nextInt(1000), 0, 0);
        var m = random.nextInt(10) - 5;
        output.add(first);
        for(int i = 1; i < length; i++) {
            if(i == length - 1)
            output.add(new WavePoint(i, first.getX() + (m * i), first.getY() + (m * i), 2, i));
            else
            output.add(new WavePoint(i, first.getX() + (m * i), first.getY() + (m * i), 0, i));
        }
        return output;
    }
    
}
