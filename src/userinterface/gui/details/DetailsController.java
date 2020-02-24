package userinterface.gui.details;

import domein.DomeinController;
import javafx.fxml.FXMLLoader;
import userinterface.gui.main.MainScreenController;

import java.io.IOException;

public class DetailsController {
    private final MainScreenController mainScreenController;
    private final DomeinController domeinController;

    private DetailsController(MainScreenController mainScreenController) {
        this.mainScreenController = mainScreenController;
        this.domeinController = mainScreenController.getDomeinController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Details.fxml"));
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
