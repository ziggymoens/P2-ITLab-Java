package main;

import domein.DomeinController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import userinterface.gui.main.MainScreenController;

public class ITLab extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        DomeinController domeinController = new DomeinController();
        Scene scene = new Scene(new MainScreenController(domeinController));
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
