package agh.ics.oop.model;

import agh.ics.oop.model.util.Parameters;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.random;

//testy sie zepsuly btw
public class Animal implements WorldElement {

    protected int energy;
    protected MapDirection currentDirection;
    protected Vector2d location;
    protected List<Integer> genes = new ArrayList<>();
    protected int age = 0;
    protected int plantsEaten = 0;
    protected int offspring = 0;
    protected boolean dead = false;
    protected Parameters parameters;

    public Animal(Vector2d location, Parameters parameters) {      // do spawnowania zwierzaków zupełnie nowych
        this.location = location;
        this.parameters = parameters;
        this.currentDirection = MapDirection.NORTH;
        this.energy = parameters.startingEnergy();
        makeGenes();
    }

    public Animal(Vector2d location, List<Integer> genes, Parameters parameters) {     // do spawnowania potomstwa
        this.location = location;
        this.currentDirection = MapDirection.NORTH;
        this.genes = genes;
        this.energy = 2*parameters.childEnergy();
        this.parameters = parameters;
    }


    private void makeGenes(){
        for(int i = 0; i < parameters.geneLength(); i++){
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

    public void move(WorldMap map) {
        int currMove = genes.get(age % parameters.geneLength());
        currentDirection = currentDirection.change(currMove);
        Vector2d newLocation = location.add(currentDirection.toUnitVector());

        if(map.canMoveTo(newLocation)){
            location = newLocation;
            if(location.getX() < 0){
                location.setX(map.getBounds().upperRight().getX());
            }
            else if(location.getX() > map.getBounds().upperRight().getX()){
                location.setX(0);
            }
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
        energy += parameters.eatingEnergy();
    }

    public Animal breed(Animal mate){
        offspring++;
        mate.increaseOffspring();
        energy -= parameters.childEnergy();
        mate.setEnergy(mate.getEnergy()-parameters.childEnergy());

        List<Integer> newGenes = combineGenes(mate);

        return new Animal(location, newGenes, parameters);
    }

    protected List<Integer> combineGenes(Animal mate){
        float energy1 = energy;
        float energy2 = mate.getEnergy();
        int genInput1 = Math.round(energy1*(parameters.geneLength()/(energy1+energy2)));
        int genInput2 = parameters.geneLength()-genInput1;

        List<Integer> genes2 = mate.getGenes();
        int side = (int) Math.round(Math.random());
        List<Integer> newGenes = new ArrayList<>();

        if(side == 0){
            for(int i = 0; i < parameters.geneLength(); i++){
                if(i < genInput1) {
                    newGenes.add(genes.get(i));
                } else {
                    newGenes.add(genes2.get(i));
                }
            }
        } else {
            for(int i = 0; i < parameters.geneLength(); i++){
                if(i < genInput2) {
                    newGenes.add(genes2.get(i));
                } else {
                    newGenes.add(genes.get(i));
                }
            }
        }

        // Mutacja
        int mutatedGenes = (int)Math.round(Math.random()*parameters.geneLength());
        for(int i = 0; i < mutatedGenes; i++){
            int whichGene = (int)Math.round(Math.random()*(parameters.geneLength()-1));
            newGenes.set(whichGene, (int)Math.round(Math.random()*7));
        }

        return newGenes;
    }
}
