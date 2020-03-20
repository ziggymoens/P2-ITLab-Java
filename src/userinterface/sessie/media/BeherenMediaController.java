package userinterface.sessie.media;

import domein.controllers.DomeinController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class BeherenMediaController {
    private DomeinController domeinController;
    @FXML
    private ImageView imgmedia;

    public BeherenMediaController (DomeinController domeinController) {
        this.domeinController = domeinController;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("BeherenMedia.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        imgmedia.setImage(new Image("storage/media/default-placeholder.png"));

    }

}
