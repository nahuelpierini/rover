package com.kata.rover.service.Impl;

import com.kata.rover.entity.Coordinate;
import com.kata.rover.entity.Map;
import com.kata.rover.entity.Obstacle;
import com.kata.rover.entity.Rover;
import com.kata.rover.enums.Direction;
import com.kata.rover.exceptions.ResourceNotFoundException;
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

    public Rover createRover(Integer mapId){

        randomRoverInitialPosition(mapId);

       int initialPositionX = positionXAndY[0];
       int initialPositionY = positionXAndY[1];
       Direction initialDirection = Direction.NORTH;

       Rover newRover = new Rover();

        newRover.setInitialPositionX(initialPositionX);
        newRover.setNewPositionX(initialPositionX);
        newRover.setInitialPositionY(initialPositionY);
        newRover.setNewPositionY(initialPositionY);
        newRover.setPointer(initialDirection);

        roverRepository.save(newRover);

       return newRover;
    }

    public void randomRoverInitialPosition(Integer mapId){

        Map existingMap = mapRepository.findById(mapId)
                .orElseThrow(()-> new ResourceNotFoundException("Map not found"));
        List<Obstacle> obstacleList = obstacleRepository.findAll();

        Random random = new Random();

        int initialPositionX = 0;
        int initialPositionY = 0;

        initialPositionX = random.nextInt(existingMap.getWidth());
        initialPositionY = random.nextInt(existingMap.getHeight());
        if (!obstacleList.isEmpty()){
            for(Obstacle obstacle : obstacleList){
                if (initialPositionX == obstacle.getCoordinateX() || initialPositionY == obstacle.getCoordinateY()){
                    System.out.println("The Rover can´t land");
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

        int maxWidth = existingMap.getWidth();
        int maxHeight = existingMap.getHeight();
        int positionX = existingRover.getNewPositionX();
        int positionY = existingRover.getNewPositionY();
        Direction pointer = existingRover.getPointer();

        for (char ch : coordinates.getCommands().toCharArray()){

            switch (ch) {
                case 'F':
                    if (pointer == NORTH){
                        if (positionY == (maxHeight-1)){
                            for (Obstacle o : obstacleList){
                                if (o.getCoordinateY() == 0 && o.getCoordinateX() == positionX){
                                    existingRover.setPointer(pointer);
                                    existingRover.setNewPositionX(positionX);
                                    existingRover.setNewPositionY(positionY);
                                    roverRepository.save(existingRover);
                                    return existingRover;
                                }
                            }
                            positionY = 0;
                        } else {
                            for (Obstacle o : obstacleList){
                                if (o.getCoordinateY() == (positionY + 1) && o.getCoordinateX() == positionX){
                                    existingRover.setPointer(pointer);
                                    existingRover.setNewPositionX(positionX);
                                    existingRover.setNewPositionY(positionY);
                                    roverRepository.save(existingRover);
                                    return existingRover;
                                }
                            }
                            positionY = positionY + 1;
                        }
                    }
                    else if (pointer == SOUTH){
                        if (positionY == 0){
                            for (Obstacle o : obstacleList){
                                if (o.getCoordinateY() == (maxHeight-1) && o.getCoordinateX() == positionX){
                                    existingRover.setPointer(pointer);
                                    existingRover.setNewPositionX(positionX);
                                    existingRover.setNewPositionY(positionY);
                                    roverRepository.save(existingRover);
                                    return existingRover;
                                }
                            }
                            positionY = (maxHeight-1);
                        } else {
                            for (Obstacle o : obstacleList){
                                if (o.getCoordinateY() == (positionY - 1) && o.getCoordinateX() == positionX){
                                    existingRover.setPointer(pointer);
                                    existingRover.setNewPositionX(positionX);
                                    existingRover.setNewPositionY(positionY);
                                    roverRepository.save(existingRover);
                                    return existingRover;
                                }
                            }
                            positionY = positionY - 1;
                        }
                    }
                    else if (pointer == EAST){
                        if (positionX == (maxWidth-1)){
                            for (Obstacle o : obstacleList){
                                if (o.getCoordinateX() == 0 && o.getCoordinateY() == positionY){
                                    existingRover.setPointer(pointer);
                                    existingRover.setNewPositionX(positionX);
                                    existingRover.setNewPositionY(positionY);
                                    roverRepository.save(existingRover);
                                    return existingRover;
                                }
                            }
                            positionX = 0;
                        }else {
                            for (Obstacle o : obstacleList){
                                if (o.getCoordinateX() == (positionX + 1) && o.getCoordinateY() == positionY){
                                    existingRover.setPointer(pointer);
                                    existingRover.setNewPositionX(positionX);
                                    existingRover.setNewPositionY(positionY);
                                    roverRepository.save(existingRover);
                                    return existingRover;
                                }
                            }
                            positionX = positionX + 1;
                        }
                    }
                    else {
                        if (positionX == 0){
                            for (Obstacle o : obstacleList){
                                if (o.getCoordinateX() == (maxWidth-1) && o.getCoordinateY() == positionY){
                                    existingRover.setPointer(pointer);
                                    existingRover.setNewPositionX(positionX);
                                    existingRover.setNewPositionY(positionY);
                                    roverRepository.save(existingRover);
                                    return existingRover;
                                }
                            }
                            positionX = (maxWidth-1);
                        }else{
                            for (Obstacle o : obstacleList){
                                if (o.getCoordinateX() == (positionX -1) && o.getCoordinateY() == positionY){
                                    existingRover.setPointer(pointer);
                                    existingRover.setNewPositionX(positionX);
                                    existingRover.setNewPositionY(positionY);
                                    roverRepository.save(existingRover);
                                    return existingRover;
                                }
                            }
                        positionX = positionX - 1;
                        }
                    }
                    break;
                case 'L':
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
                    break;
                case 'R':
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
                    break;
            }

        }

        existingRover.setPointer(pointer);
        existingRover.setNewPositionX(positionX);
        existingRover.setNewPositionY(positionY);
        roverRepository.save(existingRover);
        return existingRover;
    }


}
