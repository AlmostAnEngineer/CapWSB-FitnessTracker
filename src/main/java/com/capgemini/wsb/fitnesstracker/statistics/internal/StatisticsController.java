package com.capgemini.wsb.fitnesstracker.statistics.internal;

import com.capgemini.wsb.fitnesstracker.statistics.api.Statistics;
import com.capgemini.wsb.fitnesstracker.statistics.api.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpResponse;
import java.util.List;

@RestController
@RequestMapping("/v1/statistics")
@RequiredArgsConstructor
public class StatisticsController {
    private final StatisticsRepository statisticsRepository;
    private final StatisticsServiceImpl statisticsService;
    private final StatisticsMapper statisticsMapper;

    @GetMapping
    public ResponseEntity<List<StatisticsDto>> getStatisticsEndpoint() {
        List<StatisticsDto> allStatistics = statisticsService.getAllStatistics()
                .stream().map(StatisticsMapper::toDto).toList();
        if(allStatistics.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(allStatistics, HttpStatus.OK);
        }
    }
}
