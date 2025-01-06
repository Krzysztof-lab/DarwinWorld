package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.SphericalMap;
import agh.ics.oop.model.WorldMap;
import agh.ics.oop.model.util.IncorrectPositionException;

public class Main {
    public static void main(String[] args) throws IncorrectPositionException {
        System.out.println("Start");

        WorldMap map = new SphericalMap(1,30,30,20);
        var simulation1 = new Simulation(map,20,10);
        simulation1.run();

        /*
        var map = new SphericalMap(1,20,20,2);
        Animal animal = new Animal();
        try {
            map.place(animal);
        } catch (IncorrectPositionException e) {
            System.out.println("Nie można umieścić zwierzęcia: " + e.getMessage());
        }
        System.out.println(map);
        int n = animal.getGenes().size();
        for (int i = 0; i < n; i++) {
            map.move(animal);
            System.out.println(map);
            System.out.println(animal.getEnergy());
        }*/
    }
}

