package agh.ics.oop.model;

import java.util.List;

public class AnimalFactory {

    public static Animal createAnimal(String animalType, Vector2d location) {
        return switch (animalType) {
            case "Normal" -> new Animal(location);
            case "Aging" -> new AgingAnimal(location);
            default -> throw new IllegalStateException("Unexpected value: " + animalType);
        };
    }
}
