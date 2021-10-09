package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimplePercentileCalculatorTests {
    private SimplePercentileCalculator calculator = new SimplePercentileCalculator();
    private List<Integer> dataset = Arrays.asList(43, 54, 56, 61, 62, 66, 68, 69, 69, 70, 71, 72, 77, 78, 79, 85, 87, 88, 89, 93, 95, 96, 98, 99, 99);

    @BeforeEach
    void shuffleDataset() {
        Collections.shuffle(dataset);
    }

    @Test
    void givenPercentile90th_calculatePercentile_runsCorrectly() {
        var percentile = calculator.computePercentile(dataset, 90);

        assertEquals(98, percentile);
    }

    @Test
    void givenPercentile20th_calculatePercentile_runsCorrectly() {
        var percentile = calculator.computePercentile(dataset, 20);

        assertEquals(64, percentile);
    }

    @Test
    void givenMedianPercentile_calculatePercentile_runsCorrectly() {
        var percentile = calculator.computePercentile(dataset, 50);

        assertEquals(77, percentile);
    }
}
