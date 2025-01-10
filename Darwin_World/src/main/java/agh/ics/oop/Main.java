package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.SphericalMap;
import agh.ics.oop.model.WorldMap;
import agh.ics.oop.model.util.IncorrectPositionException;
import javafx.application.Application;

public class Main {
    public static void main(String[] args) throws IllegalArgumentException{
        System.out.println("Start");

        try {
            Application.launch(SimulationApp.class, args);
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("End");
    }
}

