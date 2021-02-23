package com.nobblecrafts.zsystemtester.services;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.nobblecrafts.zsystemtester.models.Wave;
import com.nobblecrafts.zsystemtester.models.WavePoint;

import org.springframework.stereotype.Component;

@Component
public class SystemTesterSupplier {
    
    public Wave createRandomWave(int length) {
        return Wave.builder()
            .userId(UUID.randomUUID().toString())
            .points(this.createRandomPointList(length))
            .build();
    }

    private List<WavePoint> createRandomPointList(int length) {
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

}
