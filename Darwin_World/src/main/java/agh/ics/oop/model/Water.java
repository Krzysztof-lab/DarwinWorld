package agh.ics.oop.model;

import agh.ics.oop.model.util.Boundary;
import javafx.scene.image.ImageView;

public class Water implements WorldElement {

    private final Vector2d waterSource;
    private Boundary waterBounds;

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

    public void ebbOrFlow(int day, int range, int time){
        if((day/((range-1)*time))%2==0) //przypływ
        {
            System.out.println("PRZYPLYW");
            flow();
        }
        else{
            System.out.println("ODPLYW");
            ebb();
        }

    }

    private void flow(){
        Vector2d newUpperRight = waterBounds.upperRight().add(new Vector2d(1, 1));
        Vector2d newLowerLeft = waterBounds.lowerLeft().add(new Vector2d(-1, -1));
        if(newUpperRight.follows(newLowerLeft)) {
            waterBounds = new Boundary(newLowerLeft, newUpperRight);
        }
    }

    private void ebb(){
        Vector2d newUpperRight = waterBounds.upperRight().add(new Vector2d(-1, -1));
        Vector2d newLowerLeft = waterBounds.lowerLeft().add(new Vector2d(1, 1));
        if(newUpperRight.follows(newLowerLeft)) {
            waterBounds = new Boundary(newLowerLeft, newUpperRight);
        }
    }

    @Override
    public String toString(){
        return "~";
    }

}
