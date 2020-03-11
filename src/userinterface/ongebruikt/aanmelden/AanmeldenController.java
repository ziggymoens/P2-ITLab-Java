package userinterface.ongebruikt.aanmelden;

import domein.controllers.DomeinController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class AanmeldenController extends BorderPane {
    private DomeinController domeinController;

    public AanmeldenController(DomeinController domeinController) {
        this.domeinController = domeinController;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Aanmelden.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public void aanmelden() {

    }
}
