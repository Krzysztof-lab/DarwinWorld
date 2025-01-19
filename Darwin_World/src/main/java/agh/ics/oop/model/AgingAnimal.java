package agh.ics.oop.model;

import agh.ics.oop.model.util.Genes;
import agh.ics.oop.model.util.Parameters;
import java.util.List;
import static java.lang.Math.min;

public class AgingAnimal extends Animal {

    // Starość nie radość
    private static final float AGING_START = 30f;
    private static final float OLD_AGE = 60f;
    private static final float SKIP_TURN = 80/((OLD_AGE-AGING_START)*100);

    public AgingAnimal(Vector2d location, Parameters parameters) {
        super(location, parameters);
    }

    public AgingAnimal(Vector2d location, List<Integer> genes, Parameters parameters) {
        super(location, genes, parameters);
    }

    @Override
    public void move(WorldMap map) {
        if(age < AGING_START || Math.random() > min(0.8, (age-AGING_START)*SKIP_TURN)){
            super.move(map);
        }
        else{
            age++;
            energy--;
            if(energy == 0){
                dead = true;
            }
        }
    }

    @Override
    public AgingAnimal breed(Animal mate){
        offspring++;
        mate.increaseOffspring();
        energy -= parameters.childEnergy();
        mate.setEnergy(mate.getEnergy()-parameters.childEnergy());

        List<Integer> newGenes = Genes.combineGenes(this, mate, parameters.geneLength());

        return new AgingAnimal(location, newGenes, parameters);
    }
}
