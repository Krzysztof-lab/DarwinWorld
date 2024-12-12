package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.HashMap;
import java.util.Map;

public class RectangularMap extends AbstractWorldMap {

    private final Boundary bounds;

    public RectangularMap(int id, int width, int height) {
        Vector2d upperRight = new Vector2d(width-1,height-1);
        Vector2d lowerLeft = new Vector2d(0, 0);
        this.bounds = new Boundary(lowerLeft, upperRight);
//        assignID();
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.follows(bounds.lowerLeft()) && position.precedes(bounds.upperRight()) && super.canMoveTo(position);
    }

    @Override
    public Boundary getCurrentBounds() {
        return bounds;
    }

}
