package com.kata.rover.service;

import com.kata.rover.entity.Map;
import com.kata.rover.exceptions.ResourceNotFoundException;
import com.kata.rover.repository.MapRepository;
import com.kata.rover.service.Impl.MapServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MapServiceTest {

    @Mock
    private MapRepository mapRepository;

    @InjectMocks
    private MapServiceImpl mapService;

    @Test
    public void testCreateMap(){

        Map inputMap = new Map();
        inputMap.setHeight(10);
        inputMap.setWidth(10);

        when(mapRepository.save(any(Map.class))).thenReturn(inputMap);

        Map createdMap = mapService.createMap(inputMap);


        assertNotNull(createdMap);
        assertEquals(inputMap.getWidth(), createdMap.getWidth());
        assertEquals(inputMap.getHeight(),createdMap.getHeight());
    }

    @Test
    public void testGetMapByIdWhenExists(){

        int id = 1;
        Map mockMap = new Map();
        mockMap.setId(id);
        when(mapRepository.findById(id)).thenReturn(Optional.of(mockMap));

        Map result = mapService.getMapById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());

    }

    @Test
    public void testGetMapByIdWhenMapDoesNotExist() {
        int id = 1;
        when(mapRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> mapService.getMapById(id));
    }
}
