package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.SphericalMap;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.WorldMap;
import agh.ics.oop.model.util.IncorrectPositionException;
import agh.ics.oop.model.util.Parameters;
import javafx.application.Application;

import java.lang.reflect.Parameter;
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
        Parameters parameters = new Parameters(5,5,3,2,5,30,10,20,15,6);
        Simulation simulation = new Simulation(map, parameters);
        simulation.run();


        System.out.println("End");
    }

}

