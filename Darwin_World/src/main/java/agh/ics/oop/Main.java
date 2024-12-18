package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.Boundary;
import agh.ics.oop.model.SphericalMap;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.util.IncorrectPositionException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello");
        var map = new SphericalMap(1,30,30,400);
        Animal animal = new Animal();
        try {
            map.place(animal);
        } catch (IncorrectPositionException e) {
            System.out.println("Nie można umieścić zwierzęcia: " + e.getMessage());
        }
        System.out.println(map);
    }
}
