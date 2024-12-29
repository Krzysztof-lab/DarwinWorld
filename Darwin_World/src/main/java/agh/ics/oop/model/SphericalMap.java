package agh.ics.oop.model;

import agh.ics.oop.model.util.Boundary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SphericalMap extends AbstractWorldMap {

    private final Boundary bounds;
    private final Map<Vector2d, Plant> plants = new HashMap<>();
    public SphericalMap(int id, int width, int height,int numberOfPlants) {
        Vector2d upperRight = new Vector2d(width-1,height-1);
        Vector2d lowerLeft = new Vector2d(0, 0);
        this.bounds = new Boundary(lowerLeft, upperRight);
        generatePlants(numberOfPlants);
    }

    private void generatePlants(int numberOfPlants) {
        Random random = new Random();
        int Height = getBounds().upperRight().getY();
        int middleStart = (int) Math.floor(Height * 0.4);
        int middleEnd = (int) Math.ceil(Height * 0.6);
        int x, y;
        for (int i = 0; i < numberOfPlants; i++) {
            double randomValue = random.nextDouble();
            boolean switchPlace = randomValue >= 0.2;
            do {
                x = random.nextInt(getBounds().upperRight().getX() + 1);
                if (switchPlace) {
                    y = random.nextInt(middleEnd - middleStart + 1) + middleStart;
                } else {
                    if (random.nextBoolean()) {
                        // Zakres od 0 do middleStart
                        y = random.nextInt(middleStart + 1);
                    } else {
                        // Zakres od middleEnd do Height
                        y = random.nextInt(Height - middleEnd + 1) + middleEnd;
                    }
                }
            }
            while (plants.containsValue(new Vector2d(x, y)));
            plants.put(new Vector2d(x, y), new Plant(new Vector2d(x, y)));
        }

    }
    @Override
    public WorldElement objectAt(Vector2d position) {
        WorldElement object = super.objectAt(position);
        if(object != null) {
            return object;
        }
        return plants.get(position);
    }
    
    //Dla biegunów, trzeba zmienić po dodaniu przypływów
    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.getY() <= bounds.upperRight().getY() && position.getY() >= bounds.lowerLeft().getY();
    }

    @Override
    public ArrayList<WorldElement> getElements() {
        ArrayList<WorldElement> elements = super.getElements();
        elements.addAll(plants.values());
        return elements;
    }

    public Boundary getBounds() {
        return bounds;
    }
}
