package com.example.demo;

import javax.validation.constraints.NotNull;

public class QueryPercentileRequest {
    @NotNull
    private Integer poolId;
    @NotNull
    private Double percentile;

    public QueryPercentileRequest() {
    }

    public QueryPercentileRequest(Integer poolId, Double percentile) {
        this.poolId = poolId;
        this.percentile = percentile;
    }

    public Integer getPoolId() {
        return poolId;
    }

    public void setPoolId(Integer poolId) {
        this.poolId = poolId;
    }

    public Double getPercentile() {
        return percentile;
    }

    public void setPercentile(Double percentile) {
        this.percentile = percentile;
    }
}
