package agh.ics.oop.model;

import agh.ics.oop.model.util.Boundary;

import java.util.HashMap;
import java.util.Map;

public class WaterMap extends AbstractWorldMap {

    private final Boundary bounds;
    private final Map<Vector2d, Plant> plants = new HashMap<>();
    public WaterMap(int width, int height,int numberOfPlants) {
        Vector2d upperRight = new Vector2d(width-1,height-1);
        Vector2d lowerLeft = new Vector2d(0, 0);
        this.bounds = new Boundary(lowerLeft, upperRight);
        //generateWater();
    }
    @Override
    public boolean canMoveTo(Vector2d position) {
        return false;
    }

    public Boundary getBounds() {
        return bounds;
    }

    @Override
    public void generatePlants(int numberOfPlants) {

    }
}