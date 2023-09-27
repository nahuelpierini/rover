package com.kata.rover.repository;

import com.kata.rover.entity.Rover;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoverRepository extends JpaRepository<Rover, Integer> {
}
