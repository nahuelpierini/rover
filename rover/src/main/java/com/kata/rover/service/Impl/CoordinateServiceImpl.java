package com.kata.rover.service.Impl;

import com.kata.rover.entity.Coordinate;
import com.kata.rover.exceptions.ResourceNotFoundException;
import com.kata.rover.repository.CoordinateRepository;
import com.kata.rover.service.CoordinateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoordinateServiceImpl implements CoordinateService {
    @Autowired
    private CoordinateRepository coordinateRepository;


    @Override
    public List<Coordinate> getCoordinates() {
        return coordinateRepository.findAll();
    }

    @Override
    public Coordinate setCoordinates(String commands) {
        Coordinate newCoordinates = new Coordinate();
        newCoordinates.setCommands(commands);

        return   coordinateRepository.save(newCoordinates);
    }

    @Override
    public Coordinate updateCoordinates(String commands, Integer id) {
        Coordinate existingCoordinate = coordinateRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Coordinate not found"));

        existingCoordinate.setCommands(commands);

        return coordinateRepository.save(existingCoordinate);
    }
}
