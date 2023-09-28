package com.kata.rover.service;

import com.kata.rover.entity.Map;
import com.kata.rover.entity.Obstacle;
import com.kata.rover.repository.MapRepository;
import com.kata.rover.repository.ObstacleRepository;
import com.kata.rover.service.Impl.ObstacleServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ObstacleServiceTest {

    @InjectMocks
    private ObstacleServiceImpl obstacleService;

    @Mock
    private ObstacleRepository obstacleRepository;

    @Mock
    private MapRepository mapRepository;


    @Test
    public void testGetAllObstacles() {
        List<Obstacle> obstacles = new ArrayList<>();
        obstacles.add(new Obstacle());

        when(obstacleRepository.findAll()).thenReturn(obstacles);

        List<Obstacle> result = obstacleService.getAllObstacles();

        assertEquals(obstacles.size(), result.size(), "Expected and actual obstacle counts should match.");
        assertEquals(obstacles.get(0), result.get(0), "Obstacle objects should match.");
    }

    @Test
    public void testCreateRandomObstacles() {
        int mapId = 1;
        Map existingMap = new Map();
        existingMap.setWidth(10);
        existingMap.setHeight(10);

        when(mapRepository.findById(mapId)).thenReturn(Optional.of(existingMap));

        obstacleService.createRandomObstacles(mapId);

        verify(obstacleRepository, times(4)).save(any(Obstacle.class));
    }
}
