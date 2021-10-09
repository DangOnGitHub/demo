package com.example.demo;

public class QueryPercentileResponse {
    private Double quantile;
    private Integer count;

    public QueryPercentileResponse() {
    }

    public QueryPercentileResponse(Double quantile, Integer count) {
        this.quantile = quantile;
        this.count = count;
    }

    public Double getQuantile() {
        return quantile;
    }

    public void setQuantile(Double quantile) {
        this.quantile = quantile;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
