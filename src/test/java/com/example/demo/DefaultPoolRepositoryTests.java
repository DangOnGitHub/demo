package com.example.demo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DefaultPoolRepositoryTests {
    private DefaultPoolRepository poolRepository;

    @AfterEach
    void tearDown() {
        poolRepository = null;
    }

    @Test
    void givenNonExistPool_getPoolById_returnsEmpty() {
        poolRepository = new DefaultPoolRepository(new ConcurrentHashMap<>());

        var optionalPool = poolRepository.getPoolById(1);

        assertTrue(optionalPool.isEmpty());
    }

    @Test
    void givenExistPool_getPoolById_returnsPool() {
        var pool = new Pool(1, Collections.emptyList());
        poolRepository = new DefaultPoolRepository(new ConcurrentHashMap<>(Map.of(1, pool)));

        var optionalPool = poolRepository.getPoolById(1);

        assertTrue(optionalPool.isPresent());
    }

    @Test
    void createPool_insertsNewPool() {
        var pool = new Pool(1, Collections.emptyList());
        poolRepository = new DefaultPoolRepository(new ConcurrentHashMap<>());

        poolRepository.createPool(pool);

        assertEquals(poolRepository.getPoolById(1).get(), pool);
    }

    @Test
    void updatePool_appendsPoolValues() {
        var pool = new Pool(1, Collections.singletonList(1));
        poolRepository = new DefaultPoolRepository(new ConcurrentHashMap<>(Map.of(1, pool)));
        var addedPoolValues = List.of(2, 3);

        poolRepository.updatePool(1, addedPoolValues);

        assertEquals(List.of(1, 2, 3), poolRepository.getPoolById(1).get().values());
    }
}
