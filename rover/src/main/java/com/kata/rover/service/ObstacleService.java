package com.kata.rover.service;


import com.kata.rover.entity.Obstacle;

import java.util.List;

public interface ObstacleService {

    List<Obstacle> getAllObstacles();
    void createRandomObstacles(int mapId);
}
