package com.kata.rover.repository;

import com.kata.rover.entity.Map;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MapRepository extends JpaRepository<Map,Integer> {

}
