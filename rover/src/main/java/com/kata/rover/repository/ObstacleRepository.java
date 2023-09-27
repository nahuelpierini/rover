package com.kata.rover.repository;

import com.kata.rover.entity.Obstacle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ObstacleRepository extends JpaRepository<Obstacle,Integer> {
}
