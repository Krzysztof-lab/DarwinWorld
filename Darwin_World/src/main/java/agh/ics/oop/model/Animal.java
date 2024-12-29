package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.min;

public class Animal implements WorldElement {

    private int energy = 60;               //modyfikowalne
    private static final int GENE_LEN = 200;       //modyfikowalne
    private MapDirection currentDirection;
    private Vector2d location;
    private List<Integer> genes = new ArrayList<>();
    private int age = 0;
    private int plantsEaten = 0;
    private int offspring = 0;
    private boolean dead = false;

    //Rozmnażanie i jedzenie - wszystko modyfikowalne
    private static final int ENERGY_GAINED = 10;
    private static final int ENERGY_LOST = 20;
    private static final int ENERGY_REQUIRED = 30;

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

    public Animal(Vector2d location, List<Integer> genes) {     // do spawnowania potomstwa
        this.location = location;
        this.currentDirection = MapDirection.NORTH;
        this.genes = genes;
        this.energy = 2*ENERGY_LOST;
    }

    private void makeGenes(){
        for(int i = 0; i < GENE_LEN; i++){
            this.genes.add((int) Math.round((Math.random()*7)));
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
    public List<Integer> getGenes() {
        return genes;
    }
    public boolean isDead() {
        return dead;
    }
    //

    //SETTERY
    public void setEnergy(int energy) {
        this.energy = energy;
    }
    public void increaseOffspring() {
        this.offspring++;
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
        int currMove = genes.get(age);
        Vector2d newLocation = location;
        if(age < AGING_START || Math.random() > min(0.8, (age-AGING_START)*SKIP_TURN)) {
            currentDirection = currentDirection.change(currMove);
            newLocation = location.add(currentDirection.toUnitVector());
        }
        if(map.canMoveTo(newLocation)){
            location = newLocation;
            location.setX(abs(location.getX() % map.getBounds().upperRight().getX()));
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

    public Animal breed(Animal mate){
        offspring++;
        mate.increaseOffspring();
        energy -= ENERGY_LOST;
        mate.setEnergy(mate.getEnergy()-ENERGY_LOST);

        float energy1 = energy;
        float energy2 = mate.getEnergy();
        int genInput1 = Math.round(energy1*(GENE_LEN/energy1+energy2));
        int genInput2 = GENE_LEN-genInput1;

        List<Integer> genes2 = mate.getGenes();
        int side = (int) Math.round(Math.random());
        List<Integer> newGenes = new ArrayList<>();

        if(side == 0){
             for(int i = 0; i < GENE_LEN; i++){
                 if(i < genInput1) {
                     newGenes.add(genes.get(i));
                 } else {
                     newGenes.add(genes2.get(i));
                 }
             }
        } else {
            for(int i = 0; i < GENE_LEN; i++){
                if(i < genInput2) {
                    newGenes.add(genes2.get(i));
                } else {
                    newGenes.add(genes.get(i));
                }
            }
        }

        return new Animal(location, newGenes);
    }


}
