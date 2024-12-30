package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.SphericalMap;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.WaterMap;
import agh.ics.oop.model.util.IncorrectPositionException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello");
        var map = new SphericalMap(1,20,20,10);
        Animal animal = new Animal(new Vector2d(2,2));
        Animal animal1 = new Animal(new Vector2d(3, 14));
        try {
            map.place(animal);
            map.place(animal1);
        } catch (IncorrectPositionException e) {
            System.out.println("Nie można umieścić zwierzęcia: " + e.getMessage());
        }
        System.out.println(map);
        int n = 100;
        for (int i = 0; i < n; i++) {
            map.move(animal);
            System.out.println(map);
            System.out.println(animal.getEnergy());
        }
    }
}
