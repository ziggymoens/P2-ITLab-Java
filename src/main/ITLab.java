package main;

import domein.controllers.StartController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import userinterface.startUp.StartUpController;

public class ITLab extends Application {

    public static Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        ITLab.primaryStage = primaryStage;
        StartController startController = new StartController();
        Scene scene = new Scene(new StartUpController(startController));
        ITLab.primaryStage.setScene(scene);
        ITLab.primaryStage.show();

    }
}
