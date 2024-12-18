package agh.ics.oop.model;

public class Animal implements WorldElement {

    public static final Vector2d MIN_BOUNDARY = new Vector2d(0,0);
    public static final Vector2d MAX_BOUNDARY = new Vector2d(4,4);

    private MapDirection currentDirection;
    private Vector2d location;

    public Animal() {
        this(new Vector2d(2,2));
    }

    public Animal(Vector2d location) {
        this.location = location;
        this.currentDirection = MapDirection.NORTH;
    }

    public MapDirection getCurrentDirection() {
        return currentDirection;
    }

    @Override
    public Vector2d getPosition() {
        return location;
    }

    public String toString() {
        return "%s".formatted(currentDirection);
    }

    public boolean isAt(Vector2d position) {
        return location.equals(position);
    }

    /*
    void move(MoveDirection direction, MoveValidator map) {
        switch (direction) {
            case RIGHT -> currentDirection = currentDirection.next();
            case LEFT -> currentDirection = currentDirection.previous();
            case FORWARD, BACKWARD -> actualMove(direction, map);
        }
    }
    */

}
