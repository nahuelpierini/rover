package com.kata.rover.service;

import com.kata.rover.entity.Coordinate;
import com.kata.rover.entity.Map;
import com.kata.rover.entity.Obstacle;
import com.kata.rover.entity.Rover;
import com.kata.rover.enums.Direction;
import com.kata.rover.exceptions.RoverLandingException;
import com.kata.rover.repository.CoordinateRepository;
import com.kata.rover.repository.MapRepository;
import com.kata.rover.repository.ObstacleRepository;
import com.kata.rover.repository.RoverRepository;
import com.kata.rover.service.Impl.RoverServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class RoverServiceTest {

    @Mock
    private RoverRepository roverRepository;
    @Mock
    private CoordinateRepository coordinateRepository;
    @Mock
    private MapRepository mapRepository;
    @Mock
    private ObstacleRepository obstacleRepository;

    @InjectMocks
    private RoverServiceImpl roverService;


    @Test
    public void testCreateRover() throws RoverLandingException {
        Rover newRover = new Rover();
        newRover.setPointer(Direction.NORTH);
        newRover.setNewPositionY(8);
        newRover.setNewPositionX(8);
        newRover.setInitialPositionX(5);
        newRover.setInitialPositionX(5);
        newRover.setId(1);

        Map validMap = new Map();
        validMap.setWidth(10);
        validMap.setHeight(10);

        when(mapRepository.findById(1)).thenReturn(Optional.of(validMap));

        when(roverRepository.save(any(Rover.class))).thenReturn(newRover);

        Rover saveRover = roverService.createRover(1);

        verify(roverRepository, times(1)).save(any(Rover.class));

        assertEquals(1, newRover.getId());
    }

    @Test
    public void testMoveRover() {

        Coordinate coordinates = new Coordinate();
        coordinates.setCommands("RFFF");

        Rover existingRover = new Rover();
        existingRover.setPointer(Direction.NORTH);
        existingRover.setNewPositionY(8);
        existingRover.setNewPositionX(8);
        existingRover.setInitialPositionX(5);
        existingRover.setInitialPositionX(5);

        Map existingMap = new Map();
        existingMap.setHeight(10);
        existingMap.setWidth(10);

        Obstacle o1 = new Obstacle();
        o1.setCoordinateY(8);
        o1.setCoordinateX(7);

        Obstacle o2 = new Obstacle();
        o2.setCoordinateX(1);
        o2.setCoordinateY(2);

        List<Obstacle> obstacleList = new ArrayList<>();
        obstacleList.add(o1);
        obstacleList.add(o2);


        when(coordinateRepository.findById(anyInt())).thenReturn(java.util.Optional.of(coordinates));
        when(roverRepository.findById(anyInt())).thenReturn(java.util.Optional.of(existingRover));
        when(mapRepository.findById(anyInt())).thenReturn(java.util.Optional.of(existingMap));
        when(obstacleRepository.findAll()).thenReturn(obstacleList);

        Rover result = roverService.moveRover(1, 1, 1);

        verify(roverRepository, times(1)).save(existingRover);
    }

    @Test
    public void testMoveRoverForward() {

        Coordinate coordinates = new Coordinate();
        coordinates.setCommands("FRFF");
        Rover existingRover = new Rover();
        existingRover.setNewPositionX(0);
        existingRover.setNewPositionY(0);
        existingRover.setPointer(Direction.NORTH);
        Map existingMap = new Map();
        existingMap.setWidth(10);
        existingMap.setHeight(10);
        List<Obstacle> obstacleList = Arrays.asList(new Obstacle());

        // Mock repository calls
        when(coordinateRepository.findById(anyInt())).thenReturn(java.util.Optional.of(coordinates));
        when(roverRepository.findById(anyInt())).thenReturn(java.util.Optional.of(existingRover));
        when(mapRepository.findById(anyInt())).thenReturn(java.util.Optional.of(existingMap));
        when(obstacleRepository.findAll()).thenReturn(obstacleList);

        // Call the method you want to test
        Rover result = roverService.moveRover(1, 2, 3);

        // Verify the result
        assertEquals(2, result.getNewPositionX());
        assertEquals(1, result.getNewPositionY());
    }

    @Test
    public void testMoveRoverTurnLeft() {

        Coordinate coordinates = new Coordinate();
        coordinates.setCommands("L");
        Rover existingRover = new Rover();
        existingRover.setPointer(Direction.NORTH);
        Map map = new Map();
        map.setWidth(10);
        map.setHeight(10);

        when(coordinateRepository.findById(anyInt())).thenReturn(java.util.Optional.of(coordinates));
        when(roverRepository.findById(anyInt())).thenReturn(java.util.Optional.of(existingRover));
        when(mapRepository.findById(anyInt())).thenReturn(Optional.of(map));

        Rover result = roverService.moveRover(1, 2, 3);

        assertEquals(Direction.WEST, result.getPointer());
    }

    @Test
    public void testMoveRoverTurnRight() {

        Coordinate coordinates = new Coordinate();
        coordinates.setCommands("R");
        Rover existingRover = new Rover();
        existingRover.setPointer(Direction.NORTH);
        Map map = new Map();
        map.setWidth(10);
        map.setHeight(10);

        when(coordinateRepository.findById(anyInt())).thenReturn(java.util.Optional.of(coordinates));
        when(roverRepository.findById(anyInt())).thenReturn(java.util.Optional.of(existingRover));
        when(mapRepository.findById(anyInt())).thenReturn(Optional.of(map));

        Rover result = roverService.moveRover(1, 1, 1);

        assertEquals(Direction.EAST, result.getPointer());
    }

    @Test
    public void testMoveRoverEncountersObstacle() {
        Coordinate coordinates = new Coordinate();
        coordinates.setCommands("FFFFFFF");

        Rover existingRover = new Rover();
        existingRover.setPointer(Direction.NORTH);
        existingRover.setNewPositionY(1);
        existingRover.setNewPositionX(1);
        existingRover.setInitialPositionX(1);
        existingRover.setInitialPositionY(1);

        Map existingMap = new Map();
        existingMap.setHeight(10);
        existingMap.setWidth(10);

        Obstacle obstacle = new Obstacle();
        obstacle.setCoordinateY(4);
        obstacle.setCoordinateX(1);

        List<Obstacle> obstacleList = new ArrayList<>();
        obstacleList.add(obstacle);

        when(coordinateRepository.findById(anyInt())).thenReturn(java.util.Optional.of(coordinates));
        when(roverRepository.findById(anyInt())).thenReturn(java.util.Optional.of(existingRover));
        when(mapRepository.findById(anyInt())).thenReturn(java.util.Optional.of(existingMap));
        when(obstacleRepository.findAll()).thenReturn(obstacleList);

        Rover result = roverService.moveRover(1, 1, 1);

        assertEquals(1, result.getNewPositionX());
        assertEquals(3, result.getNewPositionY());
        assertEquals(Direction.NORTH, result.getPointer());

        verify(roverRepository, times(1)).save(existingRover);
    }

    @Test
    public void testMoveRoverOverNorthLimitMap() {
        Coordinate coordinates = new Coordinate();
        coordinates.setCommands("FFFFFFF");

        Rover existingRover = new Rover();
        existingRover.setPointer(Direction.NORTH);
        existingRover.setNewPositionY(0);
        existingRover.setNewPositionX(0);
        existingRover.setInitialPositionX(0);
        existingRover.setInitialPositionY(0);

        Map existingMap = new Map();
        existingMap.setHeight(5);
        existingMap.setWidth(5);

        when(coordinateRepository.findById(anyInt())).thenReturn(java.util.Optional.of(coordinates));
        when(roverRepository.findById(anyInt())).thenReturn(java.util.Optional.of(existingRover));
        when(mapRepository.findById(anyInt())).thenReturn(java.util.Optional.of(existingMap));

        Rover result = roverService.moveRover(1, 1, 1);

        assertEquals(0, result.getNewPositionX());
        assertEquals(2, result.getNewPositionY());
        assertEquals(Direction.NORTH, result.getPointer());

        verify(roverRepository, times(1)).save(existingRover);
    }

    @Test
    public void testMoveRoverOverSouthLimitMap() {
        Coordinate coordinates = new Coordinate();
        coordinates.setCommands("RRFF");

        Rover existingRover = new Rover();
        existingRover.setPointer(Direction.NORTH);
        existingRover.setNewPositionY(0);
        existingRover.setNewPositionX(0);
        existingRover.setInitialPositionX(0);
        existingRover.setInitialPositionY(0);

        Map existingMap = new Map();
        existingMap.setHeight(5);
        existingMap.setWidth(5);

        when(coordinateRepository.findById(anyInt())).thenReturn(java.util.Optional.of(coordinates));
        when(roverRepository.findById(anyInt())).thenReturn(java.util.Optional.of(existingRover));
        when(mapRepository.findById(anyInt())).thenReturn(java.util.Optional.of(existingMap));

        Rover result = roverService.moveRover(1, 1, 1);

        assertEquals(0, result.getNewPositionX());
        assertEquals(3, result.getNewPositionY());
        assertEquals(Direction.SOUTH, result.getPointer());

        verify(roverRepository, times(1)).save(existingRover);
    }

    @Test
    public void testMoveRoverOverEastLimitMap() {
        Coordinate coordinates = new Coordinate();
        coordinates.setCommands("RFF");

        Rover existingRover = new Rover();
        existingRover.setPointer(Direction.NORTH);
        existingRover.setNewPositionY(0);
        existingRover.setNewPositionX(4);
        existingRover.setInitialPositionX(4);
        existingRover.setInitialPositionY(0);

        Map existingMap = new Map();
        existingMap.setHeight(5);
        existingMap.setWidth(5);

        when(coordinateRepository.findById(anyInt())).thenReturn(java.util.Optional.of(coordinates));
        when(roverRepository.findById(anyInt())).thenReturn(java.util.Optional.of(existingRover));
        when(mapRepository.findById(anyInt())).thenReturn(java.util.Optional.of(existingMap));

        Rover result = roverService.moveRover(1, 1, 1);

        assertEquals(1, result.getNewPositionX());
        assertEquals(0, result.getNewPositionY());
        assertEquals(Direction.EAST, result.getPointer());

        verify(roverRepository, times(1)).save(existingRover);
    }

    @Test
    public void testMoveRoverOverWestLimitMap() {
        Coordinate coordinates = new Coordinate();
        coordinates.setCommands("LFF");

        Rover existingRover = new Rover();
        existingRover.setPointer(Direction.NORTH);
        existingRover.setNewPositionY(0);
        existingRover.setNewPositionX(0);
        existingRover.setInitialPositionX(0);
        existingRover.setInitialPositionY(0);

        Map existingMap = new Map();
        existingMap.setHeight(5);
        existingMap.setWidth(5);

        when(coordinateRepository.findById(anyInt())).thenReturn(java.util.Optional.of(coordinates));
        when(roverRepository.findById(anyInt())).thenReturn(java.util.Optional.of(existingRover));
        when(mapRepository.findById(anyInt())).thenReturn(java.util.Optional.of(existingMap));

        Rover result = roverService.moveRover(1, 1, 1);

        assertEquals(3, result.getNewPositionX());
        assertEquals(0, result.getNewPositionY());
        assertEquals(Direction.WEST, result.getPointer());

        verify(roverRepository, times(1)).save(existingRover);
    }
}
