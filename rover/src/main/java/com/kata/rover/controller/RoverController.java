package com.kata.rover.controller;

import com.kata.rover.entity.Rover;
import com.kata.rover.exceptions.RoverLandingException;
import com.kata.rover.service.RoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/rover")
@CrossOrigin(origins = "http://127.0.0.1:5500") // Agrega el origen de tu frontend aquí
public class RoverController {

    @Autowired
    private RoverService roverService;

    @GetMapping("/{id}")
    public Rover getRoverInitialPosition(@PathVariable Integer id) {
        return roverService.getRover(id);
    }

    @PostMapping("/{mapId}")
    public void setRoverInitialPosition(@PathVariable Integer mapId) throws RoverLandingException {
        roverService.createRover(mapId);
    }

    @PutMapping("/{roverId}/map/{mapId}/coordinates/{coordinatesId}")
    public void moveRover(@PathVariable Integer coordinatesId, @PathVariable Integer roverId, @PathVariable Integer mapId){
        roverService.moveRover(coordinatesId, roverId, mapId);
    }

}
