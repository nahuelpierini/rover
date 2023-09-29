package com.kata.rover.service;

import com.kata.rover.entity.Rover;
import com.kata.rover.exceptions.RoverLandingException;


public interface RoverService {

    Rover createRover(Integer mapId) throws RoverLandingException;
    Rover moveRover(Integer coordinatesId, Integer roverId, Integer mapId);
    Rover getRover(Integer id);
}
