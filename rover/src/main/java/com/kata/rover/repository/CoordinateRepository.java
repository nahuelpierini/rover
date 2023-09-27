package com.kata.rover.repository;

import com.kata.rover.entity.Coordinate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoordinateRepository extends JpaRepository<Coordinate,Integer> {
}
