package userinterface.main;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class StatistiekController extends AnchorPane {

    public StatistiekController() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Statistiek.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
