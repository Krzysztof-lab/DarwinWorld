package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.util.IncorrectPositionException;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class Simulation {
    private final WorldMap map;
    private Set<Animal> aliveAnimals = new HashSet<>();
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
            aliveAnimals.add(new Animal(new Vector2d(x,y)));
        }
    }

//    public LinkedList<Animal> getAliveAnimals() {
//        return aliveAnimals;
//    }

    private void grabCorpses() {
        aliveAnimals.removeIf(animal -> animal.getEnergy() <= 0);
    }

    private void moving() {
//    for(Animal animal : aliveAnimals) {
//        map.move(animal);
//    }
    aliveAnimals = map.move();
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
