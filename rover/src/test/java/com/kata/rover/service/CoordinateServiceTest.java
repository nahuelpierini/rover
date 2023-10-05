package com.kata.rover.service;

import com.kata.rover.entity.Coordinate;
import com.kata.rover.repository.CoordinateRepository;
import com.kata.rover.service.Impl.CoordinateServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CoordinateServiceTest {

    @Mock
    private CoordinateRepository coordinateRepository;

    @InjectMocks
    private CoordinateServiceImpl coordinateService;

    @Test
    public void testGetCoordinates() {

        Coordinate c1 = new Coordinate();
        c1.setId(1);
        c1.setCommands("LFFFFFR");
        Coordinate c2 = new Coordinate();
        c2.setId(2);
        c2.setCommands("FFF");
        List<Coordinate> coordinateList = new ArrayList<>();
        coordinateList.add(c1);
        coordinateList.add(c2);

        when(coordinateRepository.findAll()).thenReturn(coordinateList);

        List<Coordinate> coordinates = coordinateService.getCoordinates();

        assertNotNull(coordinates);
        assertEquals(2, coordinates.size());
        assertEquals("LFFFFFR", coordinates.get(0).getCommands());
        assertEquals("FFF", coordinates.get(1).getCommands());
    }

    @Test
    public void testSetCoordinates(){
        Coordinate c1 = new Coordinate();
        c1.setCommands("FFFLFFF");

        when(coordinateRepository.save(any(Coordinate.class))).thenReturn(c1);

        Coordinate saveCoordinate = coordinateService.setCoordinates("FFFLFFF");

        assertNotNull(saveCoordinate);
        assertEquals(c1.getCommands(), saveCoordinate.getCommands());

    }
}
