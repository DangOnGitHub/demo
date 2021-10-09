package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PoolController.class)
public class PoolControllerTests {
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();
    @MockBean
    private PoolRepository poolRepository;
    @MockBean
    private PercentileCalculator percentileCalculator;

    @Test
    void givenInvalidParams_createOrUpdatePool_returns422() throws Exception {
        var createPoolRequest = new CreatePoolRequest(1, Collections.emptyList());
        var requestBody = objectMapper.writeValueAsString(createPoolRequest);

        mockMvc
                .perform(post("/pools").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void givenNonExistPool_createOrUpdatePool_createsANewPool() throws Exception {
        var createPoolRequest = new CreatePoolRequest(1, List.of(1, 2, 3));
        var requestBody = objectMapper.writeValueAsString(createPoolRequest);
        var createPoolResponse = new CreatePoolResponse("inserted");
        var responseBody = objectMapper.writeValueAsString(createPoolResponse);
        when(poolRepository.getPoolById(1)).thenReturn(Optional.empty());
        var createdPool = new Pool(1, List.of(1, 2, 3));

        mockMvc
                .perform(post("/pools").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().json(responseBody));

        verify(poolRepository).createPool(createdPool);
    }

    @Test
    void givenExistPool_createOrUpdatePool_updatesPool() throws Exception {
        var createPoolRequest = new CreatePoolRequest(1, List.of(2, 3));
        var requestBody = objectMapper.writeValueAsString(createPoolRequest);
        var createPoolResponse = new CreatePoolResponse("appended");
        var responseBody = objectMapper.writeValueAsString(createPoolResponse);
        var pool = new Pool(1, List.of(1));
        when(poolRepository.getPoolById(1)).thenReturn(Optional.of(pool));

        mockMvc
                .perform(post("/pools").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().json(responseBody));

        verify(poolRepository).updatePool(1, createPoolRequest.getPoolValues());
    }

    @Test
    void givenNonExistPool_queryPercentile_returnsNotFound() throws Exception {
        var queryPercentileRequest = new QueryPercentileRequest(1, 99.5);
        var requestBody = objectMapper.writeValueAsString(queryPercentileRequest);
        when(poolRepository.getPoolById(1)).thenReturn(Optional.empty());

        mockMvc
                .perform(post("/pools/1/percentiles").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenInvalidParams_queryPercentile_returns422() throws Exception {
        var queryPercentileRequest = new QueryPercentileRequest();
        var requestBody = objectMapper.writeValueAsString(queryPercentileRequest);

        mockMvc
                .perform(post("/pools/1/percentiles").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void givenAPool_queryPercentile_calculatePercentile() throws Exception {
        var queryPercentileRequest = new QueryPercentileRequest(1, 50.0);
        var requestBody = objectMapper.writeValueAsString(queryPercentileRequest);
        var dataset = List.of(1, 2, 3);
        var pool = new Pool(1, dataset);
        when(poolRepository.getPoolById(1)).thenReturn(Optional.of(pool));
        var queryPercentileResponse = new QueryPercentileResponse(2.0, 3);
        var responseBody = objectMapper.writeValueAsString(queryPercentileResponse);
        when(percentileCalculator.computePercentile(dataset, 50.0)).thenReturn(2.0);

        mockMvc
                .perform(post("/pools/1/percentiles").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().json(responseBody));

        verify(percentileCalculator).computePercentile(dataset, 50.0);
    }
}
