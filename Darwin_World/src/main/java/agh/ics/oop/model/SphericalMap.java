package agh.ics.oop.model;

import agh.ics.oop.model.util.Boundary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SphericalMap extends AbstractWorldMap {

    public SphericalMap(int width, int height,int numberOfPlants) {
        super(width,height,numberOfPlants);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.getY() <= bounds.upperRight().getY() && position.getY() >= bounds.lowerLeft().getY();
    }

    @Override
    public void move(Animal animal) {
        animals.remove(animal);
        animal.move(this);
        aroundTheWorld(animal);
        animals.put(animal, animal.getPosition());
    }

    private void aroundTheWorld(Animal animal){
        Vector2d position = animal.getPosition();
        if(position.getX() > bounds.upperRight().getX()){
            position.setX(0);
        }
        else if(position.getX() < bounds.lowerLeft().getX()){
            position.setX(bounds.upperRight().getX());
        }
        animal.setLocation(position);
    }


}
