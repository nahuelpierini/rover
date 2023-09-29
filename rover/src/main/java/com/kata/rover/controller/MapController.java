package com.kata.rover.controller;

import com.kata.rover.entity.Map;
import com.kata.rover.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/map")
@CrossOrigin(origins = "http://127.0.0.1:5500") // Agrega el origen de tu frontend aquí
public class MapController {

    @Autowired
    MapService mapService;

    @GetMapping("/{id}")
    public Map getMap(@PathVariable int id){

        return mapService.getMapById(id);
    }

    @PostMapping()
    public Map createMap(@RequestBody Map map){

        return  mapService.createMap(map);
    }
}
