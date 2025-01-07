package agh.ics.oop.model;

import agh.ics.oop.model.util.Boundary;
import agh.ics.oop.model.util.IncorrectPositionException;
import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;

public abstract class AbstractWorldMap implements WorldMap {

    protected final Map<Animal, Vector2d> animals = new HashMap<>();
    //protected final Map<Vector2d, Animal> animals = new HashMap<>();
    protected final MapVisualizer visualizer = new MapVisualizer(this);
    protected final List<MapChangeListener> observers = new ArrayList<>();
    protected final UUID mapID = UUID.randomUUID();


    @Override
    public boolean place(Animal animal) {
        if(canMoveTo(animal.getPosition())) {
//            this.animals.put(animal.getPosition(), animal);
            this.animals.put(animal, animal.getPosition());
            mapChanged("Animal placed at " + animal.getPosition());
            return true;
        }
        return false;
    }

    @Override
    public void move(Animal animal) {
        Vector2d prevPos = animal.getPosition();
        animal.move(this);
//        animals.remove(prevPos);
        animals.remove(animal);
//        animals.put(animal.getPosition(), animal);
        animals.put(animal, animal.getPosition());
        mapChanged("Animal moved from " + prevPos + " to " + animal.getPosition());
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        //return this.animals.get(position);
        for(Animal entry : animals.keySet()) {
            if(entry.getPosition().equals(position)) {
                return entry;
            }
        }
        return null;
    }

    @Override
    public List<WorldElement> objectsAt(Vector2d position) {
        List<WorldElement> objects = new ArrayList<>();
        for(Animal entry : animals.keySet()) {
            if(entry.getPosition().equals(position)) {
                objects.add(entry);
            }
        }
        return objects;
    }


    public abstract boolean canMoveTo(Vector2d position);

    @Override
    public ArrayList<WorldElement> getElements() {
        return new ArrayList<>(animals.keySet());
    }

    @Override
    public ArrayList<Animal> getAnimals() {
        return new ArrayList<>(animals.values());
    }
    public abstract Boundary getBounds();

    @Override
    public String toString() {
        Boundary bounds = getBounds();
        return visualizer.draw(bounds.lowerLeft(), bounds.upperRight());
    }

    public void addObserver(MapChangeListener observer) {
        observers.add(observer);
    }

    public void removeObserver(MapChangeListener observer) {
        observers.remove(observer);
    }

    protected void mapChanged(String message) {
        for(MapChangeListener observer : observers) {
            observer.mapChanged(this, message);
        }
    }

    @Override
    public UUID getID(){
        return mapID;
    }

}
