package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.util.IncorrectPositionException;

import java.util.LinkedList;
import java.util.Random;

public class Simulation {
    private final WorldMap map;
    private final LinkedList<Animal> aliveAnimals = new LinkedList<>();
    private final int dailyGrowth;

    public Simulation(WorldMap map,int startingAnimals,int dailyGrowth) throws IncorrectPositionException {
        this.map = map;
        this.dailyGrowth = dailyGrowth;
        Random random = new Random();
        int x, y;
        Animal newborn;
        for (int i = 0; i < startingAnimals; i++) {
            do {
                x = random.nextInt(map.getBounds().upperRight().getX() + 1);
                y = random.nextInt(map.getBounds().upperRight().getY() + 1);
                newborn = new Animal(new Vector2d(x, y));
            } while (!map.place(newborn));
            aliveAnimals.add(newborn);
        }
    }

    public LinkedList<Animal> getAliveAnimals() {
        return aliveAnimals;
    }

    private void grabCorpses() {
        aliveAnimals.removeIf(animal -> animal.getEnergy() <= 0);
    }

    private void moving() {
    for(Animal animal : aliveAnimals) {
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
        for(int i=0;i<10;i++)
        {
            day();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Day "+i+": ");
            System.out.println(map);
        }
    }
}
