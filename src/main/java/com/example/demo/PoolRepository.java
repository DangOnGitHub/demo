package com.example.demo;

import java.util.List;
import java.util.Optional;

public interface PoolRepository {
    Optional<Pool> getPoolById(int id);

    void createPool(Pool pool);

    void updatePool(int poolId, List<Integer> addedValues);
}
