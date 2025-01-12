package agh.ics.oop.model;

import agh.ics.oop.model.util.Boundary;
import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;

public abstract class AbstractWorldMap implements WorldMap {

    protected final Map<Animal, Vector2d> animals = new HashMap<>();
    protected final Map<Vector2d, Plant> plants = new HashMap<>();
    protected final MapVisualizer visualizer = new MapVisualizer(this);
    protected final UUID mapID = UUID.randomUUID();


    @Override
    public boolean place(Animal animal) {
        if(canMoveTo(animal.getPosition())) {
            this.animals.put(animal, animal.getPosition());
            return true;
        }
        return false;
    }

    @Override
    public void move(Animal animal) {
        Vector2d prevPos = animal.getPosition();
        animals.remove(animal);
        animal.move(this);
        animals.put(animal, animal.getPosition());
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
    public List<Animal> animalsAt(Vector2d position) {
        List<Animal> objects = new ArrayList<>();
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
    public Map<Vector2d, Plant> getPlants(){
        return plants;
    }
    @Override
    public Map<Animal,Vector2d> getAnimals() {
        return animals;
    }
    public abstract Boundary getBounds();

    @Override
    public String toString() {
        Boundary bounds = getBounds();
        return visualizer.draw(bounds.lowerLeft(), bounds.upperRight());
    }

    @Override
    public UUID getID(){
        return mapID;
    }

}
