package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.SphericalMap;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.WorldMap;
import agh.ics.oop.model.util.IncorrectPositionException;
import javafx.application.Application;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IllegalArgumentException, IncorrectPositionException {
        System.out.println("Start");

//        try {
//            Application.launch(SimulationApp.class, args);
//        }
//        catch (IllegalArgumentException e) {
//            System.out.println(e.getMessage());
//        }

        WorldMap map = new SphericalMap(5,5,3);
        Simulation simulation = new Simulation(map, 5, 1);
        simulation.run();

//        Animal ani = new Animal(new Vector2d(3,4));
//        Animal ani2 = new Animal(new Vector2d(3,4));
//
//        Animal baby = ani.breed(ani2);
//        System.out.println(ani.getGenes());
//        System.out.println(ani2.getGenes());
//        System.out.println(baby.getGenes());





        System.out.println("End");
    }

    private static Animal animalPriority(List<Animal> competitors){
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



}

