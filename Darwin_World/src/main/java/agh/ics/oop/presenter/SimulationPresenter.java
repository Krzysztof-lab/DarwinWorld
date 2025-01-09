package agh.ics.oop.presenter;

import agh.ics.oop.model.MapChangeListener;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.WorldMap;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class SimulationPresenter implements MapChangeListener {
    private WorldMap map;
    public void setWorldMap(WorldMap worldMap) {
        this.map = worldMap;
    }

    @FXML
    private GridPane mapGrid;

    @FXML
    private Label outLabel;


    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0)); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    private void drawMap() {
        clearGrid();
        mapGrid.getColumnConstraints().add(new ColumnConstraints(100));
        mapGrid.getRowConstraints().add(new RowConstraints(100));

        for(int i=0; i<30; i++){
            Label label = new Label(Integer.toString(i));
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.getRowConstraints().add(new RowConstraints(30));
            mapGrid.add(label, 0, i+1);
        }
        for(int i=0; i<30; i++){
            Label label = new Label(Integer.toString(i));
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.getColumnConstraints().add(new ColumnConstraints(30));
            mapGrid.add(label, i+1, 0);
        }
        for (int i = 0; i <= 30; i++) {
            for (int j = 30; j >= 0; j--) {
                if (map.isOccupied(new Vector2d(i, j))) {
                    mapGrid.add(new Label(map.objectAt(new Vector2d(i, j)).toString()), i +1, j + 1);
                }
                else {
                    mapGrid.add(new Label(" "), i+ 1, j + 1);
                }
                mapGrid.setHalignment(mapGrid.getChildren().get(mapGrid.getChildren().size() - 1), HPos.CENTER);
            }
        }
    }

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        drawMap();
        outLabel.setText(message);
    }
}
