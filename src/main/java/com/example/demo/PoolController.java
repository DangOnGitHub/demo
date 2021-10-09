package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class PoolController {
    @Autowired
    private PoolRepository poolRepository;
    @Autowired
    private PercentileCalculator percentileCalculator;

    @PostMapping("/pools")
    public ResponseEntity<CreatePoolResponse> createOrUpdatePool(@Valid @RequestBody CreatePoolRequest createPoolRequest) {
        var optionalPool = poolRepository.getPoolById(createPoolRequest.getPoolId());
        var response = new CreatePoolResponse();
        if (optionalPool.isEmpty()) {
            poolRepository.createPool(new Pool(createPoolRequest.getPoolId(), createPoolRequest.getPoolValues()));

            response.setStatus("inserted");
        } else {
            var pool = optionalPool.get();
            poolRepository.updatePool(pool.id(), createPoolRequest.getPoolValues());

            response.setStatus("appended");
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/pools/{poolId}/percentiles")
    public ResponseEntity<QueryPercentileResponse> queryPercentile(
            @PathVariable int poolId,
            @Valid @RequestBody QueryPercentileRequest queryPercentileRequest
    ) {
        var optionalPool = poolRepository.getPoolById(poolId);
        if (optionalPool.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var pool = optionalPool.get();
        var percentile = percentileCalculator.computePercentile(pool.values(), queryPercentileRequest.getPercentile());
        var response = new QueryPercentileResponse(percentile, pool.values().size());

        return ResponseEntity.ok(response);
    }
}
