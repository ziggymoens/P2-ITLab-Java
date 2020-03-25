package userinterface.main;

import domein.gebruiker.Gebruiker;
import domein.interfacesDomein.IGebruiker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class HuidigeGebruikerController extends AnchorPane {

    private IGebruiker gebruiker;

    @FXML
    private TextField naam;

    @FXML
    private TextField wachtwoord;

    @FXML
    private TextField profiel;

    @FXML
    private TextField gebruikersnaam;

    @FXML
    private TextField laatstingelogd;

    @FXML
    private TextField status;

    @FXML
    private TextField barcode;

    @FXML
    private ImageView profielfoto;

    public HuidigeGebruikerController(IGebruiker gebruiker) {
        this.gebruiker = gebruiker;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HuidigeGebruiker.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        initVelden();
    }

    private void initVelden() {
        this.gebruikersnaam.setText(gebruiker.getGebruikersnaam());
        naam.setText(gebruiker.getNaam());
    }
}
