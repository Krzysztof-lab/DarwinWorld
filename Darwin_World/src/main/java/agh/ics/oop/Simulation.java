package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.util.IncorrectPositionException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Simulation {
    private final WorldMap map;
    private final LinkedList<Animal> aliveAnimals = new LinkedList<>();
    private final int dailyGrowth;
    private final List<MapChangeListener> observers = new ArrayList<>();

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
    public WorldMap getMap() {
        return map;
    }
    public int getDay() {
        return day;
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
        mapChanged("Animals moved");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        consumption();
        breeding();
        mapChanged("Animals were bred");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        growth();
        mapChanged("Plants were grown");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    private int day=1;
    public void run() {
        while(!aliveAnimals.isEmpty())
        {
            if (paused) {
                synchronized (this) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
            day();
            System.out.println("Day "+day+": ");
            day++;
            System.out.println(map);
        }
    }
    private boolean paused = false;
    public void pause() {
        paused = true;
    }

    public void resume() {
        paused = false;
        synchronized (this) {
            notify();
        }
    }

    private void mapChanged(String message) {
        for(MapChangeListener observer : observers) {
            observer.mapChanged(map, message);
        }
    }

    public void addObserver(MapChangeListener observer) {
        observers.add(observer);
    }
}
