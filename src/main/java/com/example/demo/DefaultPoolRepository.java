package com.example.demo;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class DefaultPoolRepository implements PoolRepository {
    private final ConcurrentHashMap<Integer, Pool> pools;

    public DefaultPoolRepository(ConcurrentHashMap<Integer, Pool> pools) {
        this.pools = pools;
    }

    @Override
    public Optional<Pool> getPoolById(int id) {
        return Optional.ofNullable(pools.get(id));
    }

    @Override
    public void createPool(Pool pool) {
        pools.put(pool.id(), pool);
    }

    @Override
    public void updatePool(int poolId, List<Integer> addedValues) {
        var pool = pools.get(poolId);
        var updatedPoolValues = Stream
                .concat(pool.values().stream(), addedValues.stream())
                .collect(Collectors.toList());
        var updatePool = new Pool(poolId, updatedPoolValues);
        pools.put(poolId, updatePool);
    }
}
