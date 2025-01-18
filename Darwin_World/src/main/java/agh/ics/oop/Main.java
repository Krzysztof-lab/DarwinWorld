package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.util.IncorrectPositionException;
import agh.ics.oop.model.util.Parameters;
import javafx.application.Application;

import java.lang.reflect.Parameter;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IllegalArgumentException, IncorrectPositionException {
        System.out.println("Start");

//       try {
//           Application.launch(SimulationApp.class, args);
//        }
//        catch (IllegalArgumentException e) {
//            System.out.println(e.getMessage());
//        }

//        WorldMap map = new WaterMap(5,5,3);
//        Parameters parameters = new Parameters(1,3,20,5,15,10,6);
//        Simulation simulation = new Simulation(map, parameters);
//        simulation.run();

        //given
        Animal animal1 = new Animal(new Vector2d(0,0), List.of(0,0,0,0,0,0));
        Animal animal2 = new Animal(new Vector2d(0,0), List.of(1,1,1,1,1,1));

        System.out.println(animal1.getEnergy());
        //when
        Animal child = animal1.breed(animal2);

        System.out.println(child.getGenes());
        System.out.println(animal1.getEnergy());


        System.out.println("End");
    }

}

