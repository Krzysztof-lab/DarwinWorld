package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.SphericalMap;
import agh.ics.oop.model.WorldMap;
import agh.ics.oop.model.util.IncorrectPositionException;
import javafx.application.Application;

public class Main {
    public static void main(String[] args) throws IncorrectPositionException {
        System.out.println("Start");

        WorldMap map = new SphericalMap(30,30,20);
        var simulation1 = new Simulation(map,20,10);
        simulation1.run();


        try {
            Application.launch(SimulationApp.class, args);
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("End");
    }
}

