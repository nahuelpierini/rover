package com.kata.rover.service.Impl;

import com.kata.rover.entity.Map;
import com.kata.rover.entity.Obstacle;
import com.kata.rover.exceptions.ResourceNotFoundException;
import com.kata.rover.repository.MapRepository;
import com.kata.rover.repository.ObstacleRepository;
import com.kata.rover.service.ObstacleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class ObstacleServiceImpl implements ObstacleService {

    @Autowired
    private ObstacleRepository obstacleRepository;
    @Autowired
    private MapRepository mapRepository;

    private static final double OBSTACLE_DENSITY = 0.2;

    @Override
    public List<Obstacle> getAllObstacles() {
        return obstacleRepository.findAll();
    }

    @Override
    public void createRandomObstacles(int mapId) {

        Map existingMap = mapRepository.findById(mapId).orElseThrow(()->new ResourceNotFoundException("Map doesn't exists"));

        Random random = new Random();

         int maxWidth = existingMap.getWidth();
         int maxHeight = existingMap.getHeight();

        int maxObstacles = (int)((maxWidth+maxHeight)*OBSTACLE_DENSITY);

        for (int i=0; i<maxObstacles;i++){
            createRandomObstacles(random, maxWidth, maxHeight);
        }
    }

    private void createRandomObstacles(Random random, int maxWidth, int maxHeight) {
        int x = random.nextInt(maxWidth);
        int y = random.nextInt(maxHeight);

        Obstacle newObstacle = new Obstacle();
        newObstacle.setCoordinateX(x);
        newObstacle.setCoordinateY(y);

        obstacleRepository.save(newObstacle);
    }
}
