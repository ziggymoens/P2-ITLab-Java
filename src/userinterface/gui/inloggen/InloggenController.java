package userinterface.gui.inloggen;

import domein.DomeinController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class InloggenController extends BorderPane {
    private DomeinController domeinController;

    public InloggenController(DomeinController domeinController) {
        this.domeinController = domeinController;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Inloggen.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public void inloggen(){
        //doorsturen naar Sessies bekijken
    }
}
