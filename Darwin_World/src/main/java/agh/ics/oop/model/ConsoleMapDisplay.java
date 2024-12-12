package agh.ics.oop.model;

public class ConsoleMapDisplay implements MapChangeListener{

    private int updates = 0;

    @Override
    public synchronized void mapChanged(WorldMap worldMap, String message) {
        System.out.println(message);
        System.out.println("Map: " + worldMap.getID());
        System.out.println(worldMap);
        updates++;
        System.out.println("Total updates: " + updates);
    }
}
