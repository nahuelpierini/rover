package com.kata.rover.entity;

import com.kata.rover.enums.Direction;
import jakarta.persistence.*;

@Entity
public class Rover {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int initialPositionX;
    private int initialPositionY;
    private int newPositionX;
    private int newPositionY;

    @Enumerated(EnumType.STRING)
    private Direction pointer;

    public Rover(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInitialPositionX() { return initialPositionX; }

    public void setInitialPositionX(int initialPositionX) {
        this.initialPositionX = initialPositionX;
    }

    public int getInitialPositionY() {
        return initialPositionY;
    }

    public void setInitialPositionY(int initialPositionY) {
        this.initialPositionY = initialPositionY;
    }

    public int getNewPositionX() {
        return newPositionX;
    }

    public void setNewPositionX(int newPositionX) {
        this.newPositionX = newPositionX;
    }

    public int getNewPositionY() {
        return newPositionY;
    }

    public void setNewPositionY(int newPositionY) {
        this.newPositionY = newPositionY;
    }

    public Direction getPointer() {
        return pointer;
    }

    public void setPointer(Direction pointer) {
        this.pointer = pointer;
    }
}
