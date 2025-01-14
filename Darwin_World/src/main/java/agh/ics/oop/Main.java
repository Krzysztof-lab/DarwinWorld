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

        WorldMap map = new WaterMap(5,5,3);
        Parameters parameters = new Parameters(1,3,20,5,15,10,6);
        Simulation simulation = new Simulation(map, parameters);
        simulation.run();


        System.out.println("End");
    }

}

