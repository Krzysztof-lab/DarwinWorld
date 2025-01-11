package agh.ics.oop.model;

import javafx.scene.image.ImageView;

public class Water implements WorldElement {
    private final Vector2d waterSource;
    private final int range = 0;

    public Water(Vector2d waterSource) {
        this.waterSource = waterSource;
    }

    @Override
    public Vector2d getPosition() {
        return waterSource;
    }

    @Override
    public boolean isAt(Vector2d position) {
        return false;
    }

}
