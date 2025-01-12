package agh.ics.oop.presenter;

import agh.ics.oop.model.SphericalMap;
import agh.ics.oop.model.WaterMap;
import agh.ics.oop.model.util.IncorrectPositionException;
import agh.ics.oop.model.util.Parameters;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class LoadWorldPresenter {

    @FXML private ComboBox<String> configurationComboBox;  // Dodaj ComboBox
    @FXML private Button loadButton;
    @FXML private CheckBox saveToFileCheckBox;

    @FXML
    private void initialize() {
        File folder = new File("saved_configurations");
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".json"));

        List<String> configurationNames = new ArrayList<>();
        if (files != null) {
            for (File file : files) {
                configurationNames.add(file.getName().replace(".json", ""));
            }
        }
        configurationComboBox.getItems().setAll(configurationNames);
    }

    @FXML
    private void onStartClickDefault() {
        try {
            // Otwórz nowe okno z symulacją
            FXMLLoader simulationLoader = new FXMLLoader(getClass().getResource("/simulation.fxml"));
            Scene simulationScene = new Scene(simulationLoader.load());
            SimulationPresenter simulationPresenter = simulationLoader.getController();

            simulationPresenter.setWorldMap(new SphericalMap(10,10,10));
            simulationPresenter.setSimulation(new Parameters(5,10,100,10,30, 20,5));
            simulationPresenter.setSaving(saveToFileCheckBox.isSelected());
            simulationPresenter.addObserver(simulationPresenter);

            Stage simulationStage = new Stage();
            simulationStage.setScene(simulationScene);
            simulationStage.setTitle("Default simulation");
            simulationStage.setResizable(false);
            simulationStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icon.png"))));
            simulationStage.show();
            simulationPresenter.drawMap();

            LoadMainMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void onStartClickWater() {
        try {
            // Otwórz nowe okno z symulacją
            FXMLLoader simulationLoader = new FXMLLoader(getClass().getResource("/simulation.fxml"));
            Scene simulationScene = new Scene(simulationLoader.load());

            SimulationPresenter simulationPresenter = simulationLoader.getController();

            Stage simulationStage = new Stage();
            simulationStage.setScene(simulationScene);
            simulationStage.setTitle("Simulation");
            simulationStage.setResizable(false);
            simulationStage.show();

            // Przywróć obecne okno do menu głównego
            LoadMainMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onStartClickAging() {
        try {
            // Otwórz nowe okno z symulacją
            FXMLLoader simulationLoader = new FXMLLoader(getClass().getResource("/simulation.fxml"));
            Scene simulationScene = new Scene(simulationLoader.load());

            SimulationPresenter simulationPresenter = simulationLoader.getController();

            Stage simulationStage = new Stage();
            simulationStage.setScene(simulationScene);
            simulationStage.setTitle("Simulation");
            simulationStage.setResizable(false);
            simulationStage.show();

            // Przywróć obecne okno do menu głównego
            LoadMainMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onLoadConfiguration() {
        String selectedConfig = configurationComboBox.getSelectionModel().getSelectedItem();
        if (selectedConfig != null) {
            Map<String, Object> config = loadConfiguration(selectedConfig);
            if (config != null) {
                startSimulation(config);
            }
        } else {
            showError("No configuration selected");
        }
    }

    private void startSimulation(Map<String, Object> config) {
        try {
            // Otwórz nowe okno z symulacją
            FXMLLoader simulationLoader = new FXMLLoader(getClass().getResource("/simulation.fxml"));
            Scene simulationScene = new Scene(simulationLoader.load());
            SimulationPresenter simulationPresenter = simulationLoader.getController();

            simulationPresenter.setWorldMap(new SphericalMap(30,30,30));
            //simulationPresenter.setSimulation(30,10);
            simulationPresenter.addObserver(simulationPresenter);

            Stage simulationStage = new Stage();
            simulationStage.setScene(simulationScene);
            simulationStage.setTitle("Simulation");
            simulationStage.setResizable(false);
            simulationStage.show();
            simulationPresenter.drawMap();

            LoadMainMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private Map<String, Object> loadConfiguration(String configurationName) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File("saved_configurations" + File.separator + configurationName + ".json");
            if (file.exists()) {
                return objectMapper.readValue(file, Map.class);
            } else {
                showError("Configuration file not found: " + file.getAbsolutePath());
            }
        } catch (Exception e) {
            e.printStackTrace();
            showError("Error loading configuration: " + e.getMessage());
        }
        return null;
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
















    @FXML
    private void onBackToMenuClick() {
        try {
            LoadMainMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private Button backButton;
    private void LoadMainMenu() throws IOException {
        FXMLLoader mainMenuLoader = new FXMLLoader(getClass().getResource("/mainMenu.fxml"));
        Scene mainMenuScene = new Scene(mainMenuLoader.load());
        MenuPresenter menuPresenter = mainMenuLoader.getController();
        Stage currentStage = (Stage) backButton.getScene().getWindow();
        menuPresenter.setPrimaryStage(currentStage);
        currentStage.setScene(mainMenuScene);
    }
}
