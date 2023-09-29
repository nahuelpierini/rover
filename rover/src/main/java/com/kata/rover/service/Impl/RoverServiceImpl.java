package com.kata.rover.service.Impl;

import com.kata.rover.entity.Coordinate;
import com.kata.rover.entity.Map;
import com.kata.rover.entity.Obstacle;
import com.kata.rover.entity.Rover;
import com.kata.rover.enums.Direction;
import com.kata.rover.exceptions.ResourceNotFoundException;
import com.kata.rover.exceptions.RoverLandingException;
import com.kata.rover.repository.CoordinateRepository;
import com.kata.rover.repository.MapRepository;
import com.kata.rover.repository.ObstacleRepository;
import com.kata.rover.repository.RoverRepository;
import com.kata.rover.service.RoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

import static com.kata.rover.enums.Direction.*;

@Service
public class RoverServiceImpl implements RoverService {

    @Autowired
    private RoverRepository roverRepository;
    @Autowired
    private CoordinateRepository coordinateRepository;
    @Autowired
    private MapRepository mapRepository;
    @Autowired
    private ObstacleRepository obstacleRepository;

    public static int[] positionXAndY = new int[2];


    @Override
    public Rover getRover(Integer id) {
        return roverRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Rover not found"));
    }

    public Rover createRover(Integer mapId) throws RoverLandingException {

       randomRoverInitialPosition(mapId);

       int initialPositionX = positionXAndY[0];
       int initialPositionY = positionXAndY[1];

        Rover newRover = new Rover();

        newRover.setInitialPositionX(initialPositionX);
        newRover.setNewPositionX(initialPositionX);
        newRover.setInitialPositionY(initialPositionY);
        newRover.setNewPositionY(initialPositionY);
        newRover.setPointer(Direction.NORTH);

        roverRepository.save(newRover);

       return newRover;
    }

    public void randomRoverInitialPosition(Integer mapId) throws RoverLandingException {

        Map existingMap = mapRepository.findById(mapId)
                .orElseThrow(()-> new ResourceNotFoundException("Map not found"));

        if (existingMap.getWidth() <= 0 || existingMap.getHeight() <= 0) {
            throw new RoverLandingException("Invalid map dimensions");
        }

        List<Obstacle> obstacleList = obstacleRepository.findAll();

        Random random = new Random();

        int initialPositionX = random.nextInt(existingMap.getWidth());
        int initialPositionY = random.nextInt(existingMap.getHeight());
        if (!obstacleList.isEmpty()){
            for(Obstacle obstacle : obstacleList){
                if (initialPositionX == obstacle.getCoordinateX() || initialPositionY == obstacle.getCoordinateY()){
                    throw new RoverLandingException("The Rover can´t land over an obstacle");
                }
            }
        }
        positionXAndY[0] = initialPositionX;
        positionXAndY[1] = initialPositionY;
    }

    public Rover moveRover(Integer coordinatesId, Integer roverId, Integer mapId){

        Coordinate coordinates = coordinateRepository.findById(coordinatesId)
                .orElseThrow(() -> new ResourceNotFoundException("Coordinates not found"));

        Rover existingRover = roverRepository.findById(roverId)
                .orElseThrow(()-> new RuntimeException("Rover not found"));

        Map existingMap = mapRepository.findById(mapId)
                .orElseThrow(()-> new RuntimeException("Map not found"));

        List<Obstacle> obstacleList = obstacleRepository.findAll();

        int maxWidth = (existingMap.getWidth()-1);
        int maxHeight = (existingMap.getHeight()-1);
        int roverPositionX = existingRover.getNewPositionX();
        int roverPositionY = existingRover.getNewPositionY();
        Direction pointer = existingRover.getPointer();

        for (char ch : coordinates.getCommands().toCharArray()){

            switch (ch) {
                // F : move forward L: turn left R: turn right
                case 'F':
                    if (pointer == NORTH){
                        if (roverPositionY == maxHeight){
                            for (Obstacle o : obstacleList){
                                if (o.getCoordinateY() == 0 && o.getCoordinateX() == roverPositionX){
                                    return saveRover(existingRover, pointer, roverPositionX, roverPositionY);
                                }
                            }
                            roverPositionY = 0;
                        } else {
                            for (Obstacle o : obstacleList){
                                if (o.getCoordinateY() == (roverPositionY + 1) && o.getCoordinateX() == roverPositionX){
                                    return saveRover(existingRover, pointer, roverPositionX, roverPositionY);
                                }
                            }
                            roverPositionY = roverPositionY + 1;
                        }
                    }
                    else if (pointer == SOUTH){
                        if (roverPositionY == 0){
                            for (Obstacle o : obstacleList){
                                if (o.getCoordinateY() == maxHeight && o.getCoordinateX() == roverPositionX){
                                    return saveRover(existingRover, pointer, roverPositionX, roverPositionY);
                                }
                            }
                            roverPositionY = maxHeight;
                        } else {
                            for (Obstacle o : obstacleList){
                                if (o.getCoordinateY() == (roverPositionY - 1) && o.getCoordinateX() == roverPositionX){
                                    return saveRover(existingRover, pointer, roverPositionX, roverPositionY);
                                }
                            }
                            roverPositionY = roverPositionY - 1;
                        }
                    }
                    else if (pointer == EAST){
                        if (roverPositionX == maxWidth){
                            for (Obstacle o : obstacleList){
                                if (o.getCoordinateX() == 0 && o.getCoordinateY() == roverPositionY){
                                    return saveRover(existingRover, pointer, roverPositionX, roverPositionY);
                                }
                            }
                            roverPositionX = 0;
                        }else {
                            for (Obstacle o : obstacleList){
                                if (o.getCoordinateX() == (roverPositionX + 1) && o.getCoordinateY() == roverPositionY){
                                    return saveRover(existingRover, pointer, roverPositionX, roverPositionY);
                                }
                            }
                            roverPositionX = roverPositionX + 1;
                        }
                    }
                    else {
                        if (roverPositionX == 0){
                            for (Obstacle o : obstacleList){
                                if (o.getCoordinateX() == maxWidth && o.getCoordinateY() == roverPositionY){
                                    return saveRover(existingRover, pointer, roverPositionX, roverPositionY);
                                }
                            }
                            roverPositionX = maxWidth;
                        }else{
                            for (Obstacle o : obstacleList){
                                if (o.getCoordinateX() == (roverPositionX - 1) && o.getCoordinateY() == roverPositionY){
                                    return saveRover(existingRover, pointer, roverPositionX, roverPositionY);
                                }
                            }
                        roverPositionX = roverPositionX - 1;
                        }
                    }
                    break;
                case 'L':
                    pointer = turnLeft(pointer);
                    break;
                case 'R':
                    pointer = turnRight(pointer);
                    break;
            }

        }

        return saveRover(existingRover, pointer, roverPositionX, roverPositionY);
    }


    private Rover saveRover(Rover existingRover, Direction pointer, int positionX, int positionY) {
        existingRover.setPointer(pointer);
        existingRover.setNewPositionX(positionX);
        existingRover.setNewPositionY(positionY);
        roverRepository.save(existingRover);
        return existingRover;
    }

    private Direction turnLeft(Direction pointer) {
        if (pointer == NORTH){
            pointer = WEST;
        }
        else if (pointer == WEST){
            pointer = SOUTH;
        }
        else if (pointer == SOUTH){
            pointer = EAST;
        }
        else {
            pointer = NORTH;
        }
        return pointer;
    }

    private Direction turnRight(Direction pointer) {
        if (pointer == NORTH){
            pointer = EAST;
        }
        else if (pointer == EAST){
            pointer = SOUTH;
        }
        else if (pointer == SOUTH){
            pointer = WEST;
        }
        else {
            pointer = NORTH;
        }
        return pointer;
    }


}
