package userinterface.gui.rommel.login;

import domein.DomeinController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class LoginController extends BorderPane {

    //private final MainScreenController mainScreenController;
    private final DomeinController domeinController;

    @FXML
    private Label loginLabel, wachtwoordLabel, loginError, wachtwoordError;
    @FXML
    private TextField loginVeld, wachtwoordVeld;

    public LoginController(DomeinController domeinController) {
        //this.mainScreenController = mainScreenController;
        this.domeinController = domeinController;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        //loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

}
