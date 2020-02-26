package userinterface.media;

import com.sun.scenario.effect.impl.prism.PrImage;
import domein.DomeinController;
import domein.domeinklassen.Sessie;
import domein.interfacesDomein.IGebruiker;
import domein.interfacesDomein.ISessie;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class NieuweMediaController extends AnchorPane {
    private DomeinController domeinController;
    private ISessie huidigeSessie;
    private File file;
    private String url;
    @FXML
    private ChoiceBox<ISessie> sessie;
    @FXML
    private ChoiceBox<IGebruiker> gebruiker;
    @FXML
    private ChoiceBox<String> type;
    @FXML
    private Button openFile, save;
    @FXML
    private TextField filePath, fileURL;


    public NieuweMediaController(DomeinController domeinController) {
        this.domeinController = domeinController;
        startFXML();
        initChoiceboxes();
        gebruiker.setValue(domeinController.geefIGebruiker());
        if (!domeinController.geefProfielGebruiker().equals("HOOFDVERANTWOORDELIJKE")) {
            gebruiker.setDisable(true);
        }
        initButtons();

    }

    public NieuweMediaController(DomeinController domeinController, ISessie s) {
        this.domeinController = domeinController;
        this.huidigeSessie = s;
        startFXML();
        initChoiceboxes();
        sessie.setValue(sessie.getItems().stream().filter(ss -> ss.equals(s)).findFirst().orElse(null));
        sessie.setDisable(true);
        gebruiker.setValue(domeinController.geefIGebruiker());
        if (!domeinController.geefProfielGebruiker().equals("HOOFDVERANTWOORDELIJKE")) {
            gebruiker.setDisable(true);
        }
        initButtons();
    }

    private void initButtons() {
        openFile.setOnAction(this::FileExplorer);
        openFile.setOnAction(this::save);
    }

    private void save(ActionEvent event) {
        if (!openFile.isDisable()) {
            try {
                java.nio.file.Files.copy(
                        file.toPath(),
                        new File(file.getName()).toPath(),
                        java.nio.file.StandardCopyOption.REPLACE_EXISTING,
                        java.nio.file.StandardCopyOption.COPY_ATTRIBUTES,
                        java.nio.file.LinkOption.NOFOLLOW_LINKS);
            } catch (IOException e) {
                e.printStackTrace();
            }
            domeinController.maakNieuweMedia(sessie.getSelectionModel().getSelectedItem(), gebruiker.getSelectionModel().getSelectedItem(),type.getSelectionModel().getSelectedItem(), file.getName());
        }
    }

    private void FileExplorer(ActionEvent event){
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Media Toevoegen");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.gif", "*.jpg"));
        file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            filePath.setText(file.getAbsolutePath());
        }
    }

    private void startFXML() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("NieuweMedia.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        fileURL.setDisable(true);
        openFile.setDisable(true);
    }

    private void initChoiceboxes() {
        if (domeinController.geefProfielGebruiker().equals("VERANTWOORDELIJKE")) {
            sessie.setItems(FXCollections.observableArrayList(domeinController.getISessies()));
        } else {
            sessie.setItems(FXCollections.observableArrayList((domeinController.geefSessiesVanGebruiker())));
        }
        gebruiker.setItems(FXCollections.observableArrayList(domeinController.geefIGebruikers()));
        type.setItems(FXCollections.observableArrayList(domeinController.geefMediaTypes()));
        type.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                switch (t1){
                    case "URL":
                        fileURL.setDisable(false);
                        openFile.setDisable(true);
                        break;
                    case "FOTO":
                        openFile.setDisable(false);
                        fileURL.setDisable(true);
                        break;
                    case "ONBEKEND":
                        break;
                }
            }
        });
    }
}
