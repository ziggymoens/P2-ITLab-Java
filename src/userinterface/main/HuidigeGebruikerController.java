package userinterface.main;

import domein.controllers.DomeinController;
import domein.gebruiker.Gebruiker;
import domein.interfacesDomein.IGebruiker;
import domein.interfacesDomein.IMedia;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HuidigeGebruikerController extends AnchorPane {

    private IGebruiker gebruiker;
    private DomeinController domeinController;
    private IMedia huidigeMedia;

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

    @FXML
    private Button uploaden;

    public HuidigeGebruikerController(IGebruiker gebruiker, DomeinController dc) {
        this.domeinController =dc;
        this.gebruiker = gebruiker;
        this.huidigeMedia = gebruiker.getAfb();
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
        uploaden.setOnMouseClicked(this::upload);
    }

    private void initVelden() {
        gebruikersnaam.setText(gebruiker.getGebruikersnaam());
        naam.setText(gebruiker.getNaam());
        laatstingelogd.setText(gebruiker.getLaatstIngelogd().toString());
        profiel.setText(gebruiker.getGebruikersprofiel());
        status.setText(gebruiker.getStatus());
        barcode.setText(String.valueOf(gebruiker.getBarcode()));
        vulProfielfotoIn();
    }

    public void vulProfielfotoIn(){
        if (gebruiker.getProfielfoto() != null) {
            Image image = SwingFXUtils.toFXImage(gebruiker.getProfielfoto(), null);
            profielfoto.setImage(image);
        }else{
            Image image = new Image("storage/profielfotos/profielfoto.png");
            profielfoto.setImage(image);
        }
    }

    private void upload(MouseEvent mouseEvent){
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Media Toevoegen");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.gif", "*.jpg"));
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try {
                BufferedImage image = ImageIO.read(file);
                domeinController.mediaAfbeeldingWijzigen(image, huidigeMedia);
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                vulAfbeeldingIn(huidigeMedia);
            }
        }
    }

    public void vulAfbeeldingIn(IMedia huidigeMedia){
        BufferedImage bimage = null;
        try {
            bimage = huidigeMedia.getAfbeeding();
        } catch (Exception ignored) {
        }

        if (bimage != null) {
            Image image = SwingFXUtils.toFXImage(bimage, null);
            profielfoto.setImage(image);
        }else{
            Image image = new Image("storage/profielfotos/profielfoto.png");
            profielfoto.setImage(image);
        }
    }

}
