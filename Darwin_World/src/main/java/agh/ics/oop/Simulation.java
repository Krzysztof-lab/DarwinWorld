package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.util.IncorrectPositionException;

import java.util.*;

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
        List<Animal> corpses = new ArrayList<>();
        for(Animal animal : aliveAnimals){
            if(animal.getEnergy() <= 0){
                corpses.add(animal);
                map.getAnimals().remove(animal);
            }
        }
        aliveAnimals.removeAll(corpses);
    }

    private void moving() {
        for(Animal animal : aliveAnimals) {
            map.move(animal);
        }
    }

    private void consumption() {
        Map<Vector2d, Plant> plants = map.getPlants();
        for(Animal animal : aliveAnimals){
            Vector2d position = animal.getPosition();
            if(plants.containsKey(position)) {
                Animal winner = animalPriority(map.animalsAt(position));
                if(winner == animal){
                    plants.remove(position);
                    animal.eat();
                }
            }
        }
    }

    //sprawdzaj czy maja wystarczajaca ilosc energii
    private void breeding() {
        List<Animal> children = new ArrayList<>();

        int width = map.getBounds().upperRight().getX()+1;
        int height = map.getBounds().upperRight().getY()+1;
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                Vector2d place = new Vector2d(i,j);
                List<Animal> animals = new ArrayList<>(List.copyOf(map.animalsAt(place)));
                for(int k = 0; k < (animals.size()/2); k++) {
                    Animal mate1 = animalPriority(animals);
                    animals.remove(mate1);
                    Animal mate2 = animalPriority(animals);
                    animals.remove(mate2);
                    Animal child = mate1.breed(mate2);
                    try {
                        if (map.place(child)) {
                            children.add(child);
                        }
                    } catch (IncorrectPositionException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
        aliveAnimals.addAll(children);
    }

    private Animal animalPriority(List<Animal> competitors){
        Animal winner = competitors.getFirst();
        for(Animal animal : competitors){
            if(animal.getEnergy() > winner.getEnergy()){
                winner = animal;
            }
            else if (animal.getEnergy() == winner.getEnergy() && winner != animal){
                if(animal.getAge() > winner.getAge()){
                    winner = animal;
                }
                else if(animal.getAge() == winner.getAge()){
                    if(animal.getOffspring() > winner.getOffspring()){
                        winner = animal;
                    }
                    else if(animal.getOffspring() == winner.getOffspring()){
                        if(Math.random() > 0.5){
                            winner = animal;
                        }
                    }
                }
            }
        }
        return winner;
    }

    private void growth () {
        map.generatePlants(dailyGrowth);
    }

    private void day()  {
        grabCorpses();
        moving();
        consumption();
        breeding();
        growth();
    }

    public void run()  {
        for(int i=0;i<10;i++)
        {
            day();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Day "+i+": ");
            System.out.println("Animals on map: "+aliveAnimals.size());
            for(Animal animal : aliveAnimals){
                System.out.println(animal.getPosition() +" "+ animal.getEnergy() + " "+ animal.getOffspring() );
            }
            System.out.println(map);
        }
    }
}
