package com.kata.rover.controller;

import com.kata.rover.entity.Coordinate;
import com.kata.rover.service.CoordinateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/coordinates")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class CoordinatesController {
    @Autowired
    private CoordinateService coordinateService;

    @GetMapping()
    public List<Coordinate> getCoordinates(){

        return coordinateService.getCoordinates();
    }

    @PostMapping()
    public Coordinate setCoordinates(@RequestBody String commands){

        return   coordinateService.setCoordinates(commands);
    }

    @PutMapping("/{id}")
    public Coordinate updateCoordinates(@RequestBody String commands, @PathVariable Integer id){
        return coordinateService.updateCoordinates(commands, id);
    }
}
