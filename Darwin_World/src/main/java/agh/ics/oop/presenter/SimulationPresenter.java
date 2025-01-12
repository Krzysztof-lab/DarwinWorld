package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.model.MapChangeListener;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.WorldMap;
import agh.ics.oop.model.util.IncorrectPositionException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class SimulationPresenter implements MapChangeListener {
    private int xMin = 0;
    private int xMax;
    private int yMin = 0;
    private int yMax;
    private int mapWidth;
    private int mapHeight;
    private int width ;
    private int height;
    private static final int MAP_MAX_HEIGHT = 500;
    private static final int MAP_MAX_WIDTH = 500;

    public WorldMap map;
    public void setWorldMap(WorldMap worldMap) {
        this.map = worldMap;
        yMax = map.getBounds().upperRight().getY();
        xMax = map.getBounds().upperRight().getX();
        mapWidth = xMax - xMin + 1;
        mapHeight = yMax - yMin + 1;
        width = Math.round( MAP_MAX_WIDTH /mapWidth);
        height = Math.round( MAP_MAX_HEIGHT /mapHeight);
    }

    @FXML
    private GridPane mapGrid;

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0)); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }
    public void addElements(){
        for (int i = xMin; i <= xMax; i++) {
            for (int j = yMax; j >= yMin; j--) {
                if (map.isOccupied(new Vector2d(i, j))) {
                    mapGrid.add(new Label(map.objectAt(new Vector2d(i, j)).toString()), i - xMin + 1, yMax - j + 1);
                }
                else {
                    mapGrid.add(new Label(" "), i - xMin + 1, yMax - j + 1);
                }
                mapGrid.setHalignment(mapGrid.getChildren().get(mapGrid.getChildren().size() - 1), HPos.CENTER);
            }
        }
    }

    public void drawMap() {
        clearGrid();
        mapGrid.getColumnConstraints().add(new ColumnConstraints(20));
        mapGrid.getRowConstraints().add(new RowConstraints(20));
        for(int i=0; i<mapHeight; i++){
            Label label = new Label(Integer.toString(yMax-i));
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.getRowConstraints().add(new RowConstraints(height));
            mapGrid.add(label, 0, i+1);
        }
        for(int i=0; i<mapWidth; i++){
            Label label = new Label(Integer.toString(i+xMin));
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.getColumnConstraints().add(new ColumnConstraints(width));
            mapGrid.add(label, i+1, 0);
        }
        addElements();
    }

    @FXML
    private Label outLabel;
    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        Platform.runLater(()->{
            drawMap();
            outLabel.setText(message);
        });
    }


    public void onSimulationStartClicked() throws IncorrectPositionException {
        Simulation simulation = new Simulation(this.map, 20,10);
        Thread simulationThread = new Thread(simulation::run);
        simulationThread.start();
    }
}