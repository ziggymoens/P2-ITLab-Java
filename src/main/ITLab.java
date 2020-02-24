package main;

import domein.DomeinController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import userinterface.gui.main.MainScreenController;

public class ITLab extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        DomeinController domeinController = new DomeinController();
        BorderPane borderPane = new MainScreenController(domeinController);
        //AnchorPane anchorPane = new SessieFrameController(domeinController);
        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
