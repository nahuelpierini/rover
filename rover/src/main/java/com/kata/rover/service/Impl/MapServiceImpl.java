package com.kata.rover.service.Impl;

import com.kata.rover.entity.Map;
import com.kata.rover.exceptions.ResourceNotFoundException;
import com.kata.rover.repository.MapRepository;
import com.kata.rover.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MapServiceImpl implements MapService {

    @Autowired
    MapRepository mapRepository;

    @Override
    public Map getMapById(int id) {

        return mapRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Map not found"));
    }

    public Map createMap(Map map){

        Map newMap = new Map();
        newMap.setWidth(map.getWidth());
        newMap.setHeight(map.getHeight());

        return mapRepository.save(newMap);
    }

}
