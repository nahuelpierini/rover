package com.kata.rover.service;

import com.kata.rover.entity.Map;


public interface MapService {

    Map getMapById(int id);
    Map createMap(Map map);
}
