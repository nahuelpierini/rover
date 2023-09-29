package com.kata.rover.service;

import com.kata.rover.entity.Coordinate;

import java.util.List;


public interface CoordinateService {

    List<Coordinate> getCoordinates();

    Coordinate setCoordinates(String commands);


    Coordinate updateCoordinates(String commands, Integer id);

}
