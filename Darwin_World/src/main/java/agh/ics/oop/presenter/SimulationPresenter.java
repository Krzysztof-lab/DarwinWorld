package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.model.*;
import agh.ics.oop.model.util.IncorrectPositionException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class SimulationPresenter implements MapChangeListener {
    private final int xMin = 0;
    private final int yMin = 0;
    private int xMax;
    private int yMax;
    private int mapWidth;
    private int mapHeight;
    private int width ;
    private int height;
    private static final int MAP_MAX_HEIGHT = 500;
    private static final int MAP_MAX_WIDTH = 500;

    private WorldMap map;
    private Simulation simulation;
    public void setWorldMap(WorldMap worldMap) {
        this.map = worldMap;
        yMax = map.getBounds().upperRight().getY();
        xMax = map.getBounds().upperRight().getX();
        mapWidth = xMax - xMin + 1;
        mapHeight = yMax - yMin + 1;
        width = Math.round( (float) MAP_MAX_WIDTH /mapWidth);
        height = Math.round( (float) MAP_MAX_HEIGHT /mapHeight);
    }

    public void setSimulation(int startingAnimals,int dailyGrowth) throws IncorrectPositionException {
        this.simulation = new Simulation(map, startingAnimals, dailyGrowth);
    }

    public void addObserver(MapChangeListener observer) {
        this.simulation.addObserver(observer);
    }
    @FXML
    private GridPane mapGrid;

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().getFirst());
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }
    public void addElements(){
        freeSpaces = 0;
        for (int i = xMin; i <= xMax; i++) {
            for (int j = yMax; j >= yMin; j--) {
                if (map.isOccupied(new Vector2d(i, j))) {
                    switch (map.objectAt(new Vector2d(i, j))) {
                        case Animal animal -> {
                            StackPane cellContent = new StackPane();
                            // Dodaj obrazek zwierzęcia
                            ImageView animalImage = toImageView("/monkeyW.png", width, height);
                            cellContent.getChildren().add(animalImage);
                            // Dodaj pasek energii na dole
                            ImageView energyBar = getEnergyBar(animal.getEnergy());
                            StackPane.setAlignment(energyBar, Pos.BOTTOM_CENTER);  // Ustaw pasek energii na dole
                            cellContent.getChildren().add(energyBar);
                            mapGrid.add(cellContent, i - xMin + 1, yMax - j + 1);
                        }
                        case Water water -> mapGrid.add(toImageView("/water.png", width, height), i - xMin + 1, yMax - j + 1);
                        case Plant plant -> mapGrid.add(toImageView("/grass.png", width, height), i - xMin + 1, yMax - j + 1);
                        default -> throw new IllegalStateException("Unexpected value: " + map.objectAt(new Vector2d(i, j)));
                    }
                } else {
                    mapGrid.add(new Label(" "), i - xMin + 1, yMax - j + 1);
                    freeSpaces++;
                }
                GridPane.setHalignment(mapGrid.getChildren().getLast(), HPos.CENTER);
            }
        }
    }

    public ImageView toImageView(String imagePath,int width, int height) {
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));  // Załaduj obrazek z zasobów
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        return imageView;
    }

    public ImageView getEnergyBar(int energy) {
        String imagePath;
        if (energy >= 50) {
            imagePath = "/energy_full.png";
        } else if (energy >= 10) {
            imagePath = "/energy_half.png";
        } else {
            imagePath = "/energy_empty.png";
        }

        return toImageView(imagePath, width, height/5);
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
            updateStatistics();
        });
    }

    public void onSimulationStartClicked() {
        Thread simulationThread = new Thread(simulation::run);
        simulationThread.start();
    }
    @FXML
    private Button pauseButton;
    @FXML
    public void onPauseButtonClicked() {
        if (pauseButton.getText().equals("PAUSE")) {
            simulation.pause();
            pauseButton.setText("RESUME");
        }
        else {
            simulation.resume();
            pauseButton.setText("PAUSE");
        }
    }

    @FXML    private Label animalsCountLabel;
    @FXML    private Label plantsCountLabel;
    @FXML    private Label freeSpacesCountLabel;
    @FXML    private Label mostCommonGenotypeLabel;
    @FXML    private Label avgEnergyLabel;
    @FXML    private Label avgLifespanLabel;
    @FXML    private Label avgOffspringLabel;

    private int freeSpaces;

    public void updateStatistics() {
        int aliveAnimals = simulation.getAliveAnimals().size();
        int totalPlants = simulation.getMap().getElements().size() - simulation.getMap().getAnimals().size();
        int freeSpaces = this.freeSpaces;

        Map<String, Long> genotypeCount = simulation.getMap().getAnimals().stream()
                .map(animal -> animal.getGenes().toString())
                .collect(Collectors.groupingBy(genotype -> genotype, Collectors.counting()));
        String mostCommonGenotype = genotypeCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("No Genotype");

        double averageEnergy = simulation.getAliveAnimals().stream()
                .mapToInt(Animal::getEnergy)
                .average()
                .orElse(0);

        double averageLifespanOfDeadAnimals = simulation.getMap().getAnimals().stream()
                .filter(Animal::isDead)
                .mapToLong(Animal::getAge)
                .average()
                .orElse(-1);

        double averageOffspringOfLivingAnimals = simulation.getMap().getAnimals().stream()
                .filter(animal -> !animal.isDead())
                .mapToInt(Animal::getOffspring)
                .average()
                .orElse(-1);

        //GUI
        animalsCountLabel.setText("Alive animals: " + aliveAnimals);
        plantsCountLabel.setText("Total Plants: " + totalPlants);
        freeSpacesCountLabel.setText("Free Spaces: " + freeSpaces);
        mostCommonGenotypeLabel.setText("Most Common Genotype: " + mostCommonGenotype);
        avgEnergyLabel.setText("Average Energy: " + String.format("%.2f", averageEnergy));
        avgLifespanLabel.setText("Average Lifespan (dead): " + averageLifespanOfDeadAnimals);
        avgOffspringLabel.setText("Average Offspring: " + averageOffspringOfLivingAnimals);

        //CSV
        if (csvWriter != null && saving) {
            csvWriter.printf(
                    "\"%d\";\"%d\";\"%d\";\"%d\";\"%s\";\"%.2f\";\"%.2f\";\"%.2f\"%n",
                    simulation.getDay(), // Dzień symulacji
                    aliveAnimals,
                    totalPlants,
                    freeSpaces,
                    mostCommonGenotype.replace("\"", "\"\""), // Escape dla cudzysłowów w genotypie
                    averageEnergy,
                    averageLifespanOfDeadAnimals,
                    averageOffspringOfLivingAnimals
            );
            csvWriter.flush();
        }

    }
    private boolean saving = false;
    public void setSaving(boolean saving) {
        this.saving = saving;
        initializeCsvFile();
    }

    private PrintWriter csvWriter;
    private void initializeCsvFile() {
        try {
            csvWriter = new PrintWriter(new FileWriter("statistics/simulation_statistics"+simulation.getMap().getID()+".csv", true));
            // Nagłówki
            csvWriter.println("Day;Alive Animals;Total Plants;Free Spaces;Most Common Genotype;Average Energy;Average Lifespan;Average Offspring");
            csvWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}