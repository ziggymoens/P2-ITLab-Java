package userinterface.main;

import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class HuidigeGebruikerController {

    public HuidigeGebruikerController() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HuidigeGebruiker.fxml"));
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
