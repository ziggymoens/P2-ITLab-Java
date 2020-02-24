package userinterface.gui.login;

import domein.DomeinController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import userinterface.gui.main.MainScreenController;

import java.awt.*;
import java.io.IOException;

public class LoginController {

    private final MainScreenController mainScreenController;
    private final DomeinController domeinController;

    @FXML
    private Label loginLabel, wachtwoordLabel, loginError, wachtwoordError;
    @FXML
    private TextField loginVeld, wachtwoordVeld;

    private LoginController(MainScreenController mainScreenController) {
        this.mainScreenController = mainScreenController;
        this.domeinController = mainScreenController.getDomeinController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
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
