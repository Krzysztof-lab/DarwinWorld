package agh.ics.oop.model;

import java.util.List;

import static java.lang.Math.min;

public class AgingAnimal extends Animal {

    // Starość nie radość
    private static final float AGING_START = 30f;  // oba inty modyfikowalne
    private static final float OLD_AGE = 70f;
    private static final float SKIP_TURN = 80/((OLD_AGE-AGING_START)*100);

    public AgingAnimal(Vector2d location) {
        super(location);
    }

    public AgingAnimal(Vector2d location, List<Integer> genes) {
        super(location, genes);
    }

    @Override
    public void move(AbstractWorldMap map) {
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
}
