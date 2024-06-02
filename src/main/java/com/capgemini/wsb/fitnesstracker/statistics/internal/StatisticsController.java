package com.capgemini.wsb.fitnesstracker.statistics.internal;

import com.capgemini.wsb.fitnesstracker.statistics.api.Statistics;
import com.capgemini.wsb.fitnesstracker.statistics.api.StatisticsService;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import com.capgemini.wsb.fitnesstracker.user.internal.UserDto;
import com.capgemini.wsb.fitnesstracker.user.internal.UserMapper;
import com.capgemini.wsb.fitnesstracker.user.internal.UserRepository;
import com.capgemini.wsb.fitnesstracker.user.internal.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/statistics")
@RequiredArgsConstructor
public class StatisticsController {
    private final StatisticsRepository statisticsRepository;
    private final StatisticsServiceImpl statisticsService;
    private final StatisticsMapper statisticsMapper;
    private final UserService userService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

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

    @PostMapping
    public ResponseEntity<StatisticsDto> createStatistics(@RequestBody StatisticsDto statisticsDto) {
        Optional<User> user = userRepository.findById(statisticsDto.userid());
        if(user.isPresent()) {
            List<StatisticsDto> allStats = statisticsService.getAllStatistics().stream().map(StatisticsMapper::toDto).toList();
            for(StatisticsDto statistics : allStats) {
                if(statistics.userid().equals(statisticsDto.userid())) {
                    return new ResponseEntity<>(statistics, HttpStatus.CONFLICT);
                }
            }
            Statistics statistics = statisticsMapper.toEntity(statisticsDto, null, user.get());
            Statistics created = statisticsService.createStatistics(statistics);
            return new ResponseEntity<>(StatisticsMapper.toDto(created), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping
    public ResponseEntity<StatisticsDto> updateStatistics(@RequestBody StatisticsDto statisticsDto) {
        if(statisticsDto.id() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<Statistics> actualStatistics = statisticsService.getStatistics(statisticsDto.id());
        if(actualStatistics.isPresent()) {
            Statistics newStatistics = StatisticsMapper.toEntity(statisticsDto,
                    actualStatistics.get().getId(),
                    actualStatistics.get().getUser());
            statisticsRepository.save(newStatistics);
            return new ResponseEntity<>(StatisticsMapper.toDto(newStatistics), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{userid}")
    public ResponseEntity<StatisticsDto> getStatistics(@PathVariable("userid") Long userid) {
        Optional<Statistics> statistics = statisticsService.getStatisticsForUser(userid);
        if(statistics.isPresent()) {
            Statistics statistic = statistics.get();
            StatisticsDto statisticDto = StatisticsMapper.toDto(statistic);
            return new ResponseEntity<>(statisticDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
