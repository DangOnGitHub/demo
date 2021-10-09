package com.example.demo;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SimplePercentileCalculator implements PercentileCalculator {
    @Override
    public double computePercentile(List<Integer> dataset, double percentile) {
        var sortedDataset = dataset.stream().sorted().collect(Collectors.toList());
        var index = percentile / 100 * sortedDataset.size();
        if (isWholeNumber(index)) {
            var intIndex = (int) index;
            return (sortedDataset.get(intIndex) + sortedDataset.get(intIndex - 1)) / 2;
        } else {
            var roundedIndex = (int) Math.ceil(index);
            return sortedDataset.get(roundedIndex - 1);
        }
    }

    private boolean isWholeNumber(double number) {
        return number == Math.round(number);
    }
}
