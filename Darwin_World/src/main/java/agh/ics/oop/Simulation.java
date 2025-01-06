package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.util.IncorrectPositionException;

import java.util.ArrayList;
import java.util.Random;

public class Simulation {
    private final WorldMap map;
    private final ArrayList<Animal> animals = new ArrayList<>();
    private final int dailyGrowth;
    public Simulation(WorldMap map,int startingAnimals,int dailyGrowth) throws IncorrectPositionException {
        this.map = map;
        this.dailyGrowth = dailyGrowth;
        Random random = new Random();
        int x, y;
        for (int i = 0; i < startingAnimals; i++) {
            do {
                x = random.nextInt(map.getBounds().upperRight().getX() + 1);
                y= random.nextInt(map.getBounds().upperRight().getY() + 1);
            } while (!map.place(new Animal(new Vector2d(x,y))));
            animals.add(new Animal(new Vector2d(x,y)));
        }
    }

    private void grabCorpses() {

    }
    private void moving() {
    for(Animal animal : animals) {
        map.move(animal);
    }
    }
    private void consumption() {

    }
    private void breeding() {

    }
    private void growth () {
        map.generatePlants(dailyGrowth);
    }
    private void day() {
        grabCorpses();
        moving();
        consumption();
        breeding();
        growth();
    }

    public void run() {
        for(int i=0;i<100;i++)
        {
            day();
            System.out.println("Day "+i+": ");
            System.out.println(map);
        }
    }
}
