package agh.ics.oop.model;

import javafx.scene.image.ImageView;

public interface WorldElement {

    Vector2d getPosition();

    boolean isAt(Vector2d position);

}
