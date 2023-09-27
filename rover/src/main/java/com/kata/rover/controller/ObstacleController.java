package com.kata.rover.controller;

import com.kata.rover.entity.Obstacle;
import com.kata.rover.service.ObstacleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/obstacle")
public class ObstacleController {
    @Autowired
    private ObstacleService obstacleService;


    @GetMapping()
    public List<Obstacle> getAll(){
        return obstacleService.getAllObstacles();
    }

    @PostMapping("/map/{mapId}")
    public void createObstacles(@PathVariable int mapId){

         obstacleService.createRandomObstacles(mapId);
    }
}
