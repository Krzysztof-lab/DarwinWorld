package agh.ics.oop.model;

import agh.ics.oop.model.util.Parameters;

import java.util.List;

public class AnimalFactory {

    public static Animal createAnimal(String animalType, Vector2d location, Parameters parameters) {
        return switch (animalType) {
            case "Randomness" -> new Animal(location,parameters);
            case "Once a man, twice a child" -> new AgingAnimal(location,parameters);
            default -> throw new IllegalStateException("Unexpected value: " + animalType);
        };
    }
}
