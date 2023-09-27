package com.kata.rover.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Obstacle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int coordinateX;
    private int coordinateY;

    public Obstacle(int id, int coordenateX, int coordenateY) {
        this.id = id;
        this.coordinateX = coordenateX;
        this.coordinateY = coordenateY;
    }

    public Obstacle(int coordenateX, int coordenateY) {
        this.coordinateX = coordenateX;
        this.coordinateY = coordenateY;
    }

    public Obstacle() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(int coordinateX) {
        this.coordinateX = coordinateX;
    }

    public int getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(int coordinateY) {
        this.coordinateY = coordinateY;
    }
}
