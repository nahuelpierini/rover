package com.kata.rover.service;

import com.kata.rover.entity.Rover;

public interface RoverService {

    Rover createRover(Integer mapId);
    Rover moveRover(Integer coordinatesId, Integer roverId, Integer mapId);
}
