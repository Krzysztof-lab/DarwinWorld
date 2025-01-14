package agh.ics.oop.model;

import agh.ics.oop.model.util.Boundary;
import javafx.scene.image.ImageView;

public class Water implements WorldElement {

    private final Vector2d waterSource;
    private final Boundary waterBounds;

    public Water(Vector2d waterSource) {
        this.waterSource = waterSource;
        this.waterBounds = new Boundary(waterSource, waterSource);
    }

    @Override
    public Vector2d getPosition() {
        return waterSource;
    }

    @Override
    public boolean isAt(Vector2d position) {
        return position.precedes(waterBounds.upperRight()) && position.follows(waterBounds.lowerLeft());
    }

    public Boundary getWaterBounds() {
        return waterBounds;
    }

    public void ebbOrFlow(int day, int range){
        if((day/(range-1))%2 == 0) //przypływ
        {
            flow();
        }
        else{
            ebb();
        }
    }

    private void flow(){
        if(waterBounds.lowerLeft() != waterSource) {
            waterBounds.upperRight().add(new Vector2d(1, 1));
            waterBounds.lowerLeft().add(new Vector2d(-1, -1));
        }
    }

    private void ebb(){
        if(waterBounds.lowerLeft() != waterSource) {
            waterBounds.upperRight().add(new Vector2d(-1, -1));
            waterBounds.lowerLeft().add(new Vector2d(1, 1));
        }
    }

    @Override
    public String toString(){
        return "~";
    }

}
