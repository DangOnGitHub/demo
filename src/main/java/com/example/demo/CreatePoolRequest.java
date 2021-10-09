package com.example.demo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class CreatePoolRequest {
    @NotNull
    private Integer poolId;
    @NotEmpty
    private List<Integer> poolValues;

    public CreatePoolRequest() {
    }

    public CreatePoolRequest(Integer poolId, List<Integer> poolValues) {
        this.poolId = poolId;
        this.poolValues = poolValues;
    }

    public Integer getPoolId() {
        return poolId;
    }

    public void setPoolId(Integer poolId) {
        this.poolId = poolId;
    }

    public List<Integer> getPoolValues() {
        return poolValues;
    }

    public void setPoolValues(List<Integer> poolValues) {
        this.poolValues = poolValues;
    }
}
