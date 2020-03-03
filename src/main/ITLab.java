package main;

import domeintje.DomeinController;
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
        DomeinController domeinController = new DomeinController();
        Scene scene = new Scene(new StartUpController(domeinController));
        ITLab.primaryStage.setScene(scene);
        ITLab.primaryStage.show();

    }
}
