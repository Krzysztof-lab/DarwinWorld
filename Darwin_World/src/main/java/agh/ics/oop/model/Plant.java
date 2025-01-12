package agh.ics.oop.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class Plant implements WorldElement{

    private final Vector2d plantSpot;

    public Plant(Vector2d plantSpot) {
        this.plantSpot = plantSpot;
    }

    @Override
    public Vector2d getPosition() {
        return plantSpot;
    }

    @Override
    public boolean isAt(Vector2d position) {
        return plantSpot.equals(position);
    }

    @Override
    public String toString() {
        return "*";
    }
}
