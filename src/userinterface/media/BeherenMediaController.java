package userinterface.media;

import domein.interfacesDomein.IMedia;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class BeherenMediaController extends AnchorPane {
    private IMedia media;

    private BeherenMediaController(IMedia media) {
        this.media = media;
        startFXML();
    }

    private void startFXML() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("BeherenMedia.fxml"));
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
