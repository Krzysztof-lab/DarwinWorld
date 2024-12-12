package agh.ics.oop.model;

public class Grass implements WorldElement{

    private final Vector2d grassSpot;

    public Grass(Vector2d grassSpot) {
        this.grassSpot = grassSpot;
    }

    @Override
    public Vector2d getPosition() {
        return grassSpot;
    }

    @Override
    public boolean isAt(Vector2d position) {
        return grassSpot == position;
    }

    @Override
    public String toString() {
        return "*";
    }

}
