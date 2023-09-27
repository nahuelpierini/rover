package com.kata.rover.controller;

import com.kata.rover.service.RoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rover")
public class RoverController {

    @Autowired
    private RoverService roverService;

    @PostMapping("/{mapId}")
    public void setRoverInitialPosition(@PathVariable Integer mapId){
        roverService.createRover(mapId);
    }

    @PutMapping("/{roverId}/map/{mapId}/coordinates/{coordinatesId}")
    public void moveRover(@PathVariable Integer coordinatesId, @PathVariable Integer roverId, @PathVariable Integer mapId){
        roverService.moveRover(coordinatesId, roverId, mapId);
    }

}
