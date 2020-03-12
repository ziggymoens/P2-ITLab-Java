package userinterface.startUp;

import domein.PasswordUtils;
import domein.controllers.StartController;
import domein.interfacesDomein.IGebruiker;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import language.I18N;
import main.ITLab;
import userinterface.main.MainController;

import java.io.IOException;

public class StartUpController extends AnchorPane {

    private StartController startController;
    @FXML
    private Button inloggenButton;
    @FXML
    private TextField gebruikersNaam, wachtwoordV;
    @FXML
    private PasswordField wachtwoord;
    @FXML
    private ImageView logo;
    @FXML
    private Label error, errorGebruikersnaam, errorWachtwoord;
    @FXML
    private CheckBox tonen;
    @FXML
    private Label welkom;

    public StartUpController(StartController startController) {
        this.startController = startController;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StartUp.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        Image image = new Image("storage/images/logoHoGent.png");
        this.logo.setImage(image);
        welkom = I18N.labelForKey(() -> I18N.get("welkom"));
        gebruikersNaam.setText("itlab");
        wachtwoord.setText("itlab");
        inloggenButton.setOnAction(this::inloggen);
        inloggenButton.setDefaultButton(true);
        tonen.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    String ww = wachtwoord.getText();
                    wachtwoord.setText("");
                    wachtwoordV.setText(ww);
                    wachtwoordV.setVisible(true);
                    wachtwoord.setVisible(false);
                } else {
                    String ww = wachtwoordV.getText();
                    wachtwoordV.setText("");
                    wachtwoord.setText(ww);
                    wachtwoord.setVisible(true);
                    wachtwoordV.setVisible(false);
                }
            }
        });
        tonen.setSelected(false);
    }

    private void inloggen(ActionEvent actionEvent) {
        if (gebruikersNaam.getText().isBlank()) {
            errorGebruikersnaam.setText("Gebruikersnaam ontbreekt");
        } else if (wachtwoord.getText().isBlank() & wachtwoordV.getText().isBlank()) {
            errorWachtwoord.setText("Wachtwoord ontbreekt");
        } else {
            errorWachtwoord.setText("");
            errorGebruikersnaam.setText("");
            String gebruikersnaam = gebruikersNaam.getText();
            String ww = wachtwoord.getText();
            if (tonen.isSelected()) {
                ww = wachtwoordV.getText();
            }
            IGebruiker gebruiker = startController.geefIGebruikers().stream().filter(g -> g.getGebruikersnaam().equals(gebruikersnaam)).findFirst().orElse(null);
            if (gebruiker == null || !PasswordUtils.verifyUserPassword(ww, gebruiker.getWachtwoord())) {
                error.setText("Gebruikersnaam en/of wachtwoord zijn ongeldig");
                wachtwoord.setText("");
                wachtwoordV.setText("");
            } else {
                ITLab.primaryStage.close();
                startController.setHuidigeGebruiker(gebruiker.getGebruikersnaam());
                Scene scene = new Scene(new MainController(startController.initDomeinController()));
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            }
        }
    }
}
