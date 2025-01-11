package agh.ics.oop.presenter;

import agh.ics.oop.model.SphericalMap;
import agh.ics.oop.model.WaterMap;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.Observer;

public class LoadWorldPresenter {


    @FXML
    private void onStartClickDefault() {
        try {
            // Otwórz nowe okno z symulacją
            FXMLLoader simulationLoader = new FXMLLoader(getClass().getResource("/simulation.fxml"));
            Scene simulationScene = new Scene(simulationLoader.load());

            SimulationPresenter simulationPresenter = simulationLoader.getController();
            simulationPresenter.setWorldMap(new SphericalMap(30,30,30));
            simulationPresenter.map.addObserver(simulationPresenter);
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
