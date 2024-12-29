package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.min;

public class Animal implements WorldElement {

    private int energy = 60;               //modyfikowalne
    private final int geneLen = 200;       //modyfikowalne
    private MapDirection currentDirection;
    private Vector2d location;
    private int[] genes = new int[geneLen];
    private int age = 0;
    private int plantsEaten = 0;
    private int offspring = 0;
    private boolean dead = false;

    private static final int ENERGY_GAINED = 10; //modyfikowalne
    private static final int ENERGY_LOST = 20; //modyfikowalne

    // Starość nie radość
    private static final float AGING_START = 30f;  // oba inty modyfikowalne
    private static final float OLD_AGE = 70f;      // 80% na pominięcie ruchu
    private static final float SKIP_TURN = 80/((OLD_AGE-AGING_START)*100);


    public Animal() {
        this(new Vector2d(2,2));    //losowy wektor na podstawie mapy
    }

    public Animal(Vector2d location) {      // do spawnowania zwierzaków zupełnie nowych
        this.location = location;
        this.currentDirection = MapDirection.NORTH;
        makeGenes();
    }

    public Animal(Vector2d location, int[] genes) {     // do spawnowania potomstwa
        this.location = location;
        this.currentDirection = MapDirection.NORTH;
        this.genes = genes;
    }

    private void makeGenes(){
        for(int i = 0; i < geneLen; i++){
            this.genes[i] = (int) Math.round((Math.random()*7));
        }
    }

    // GETTERY
    @Override
    public Vector2d getPosition() {
        return location;
    }
    public MapDirection getCurrentDirection() {
        return currentDirection;
    }
        public int getAge() {
        return age;
    }
    public int getEnergy() {
        return energy;
    }
    public int getPlantsEaten() {
        return plantsEaten;
    }
    public int getOffspring() {
        return offspring;
    }
    public int[] getGenes() {
        return genes;
    }
    public boolean isDead() {
        return dead;
    }
    //

    @Override
    public String toString() {
        return "%s".formatted(currentDirection);
    }

    public boolean isAt(Vector2d position) {
        return location.equals(position);
    }

    public void move(AbstractWorldMap map) {
        int currMove = genes[age];
        Vector2d newLocation = location;
        if(age < AGING_START){
            currentDirection = currentDirection.change(currMove);
            newLocation = location.add(currentDirection.toUnitVector());
        } else if (Math.random() > min(0.8, (age-AGING_START)*SKIP_TURN)) {
            currentDirection = currentDirection.change(currMove);
            newLocation = location.add(currentDirection.toUnitVector());
        }
        if(map.canMoveTo(newLocation)){
            location = newLocation;
            location.setX(location.getX() % map.getBounds().upperRight().getX());
        } else {
            currentDirection = currentDirection.change(4);
        }
        age++;
        energy--;
        if(energy == 0){
            dead = true;
        }
    }

    public void eat(){
        plantsEaten++;
        energy += ENERGY_GAINED;
    }

    public void breed(){
        offspring++;
        energy -= ENERGY_LOST;
    }


}
