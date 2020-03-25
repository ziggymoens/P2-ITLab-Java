package userinterface.sessie.media;

import domein.Media;
import domein.controllers.DomeinController;
import domein.interfacesDomein.IFeedback;
import domein.interfacesDomein.IGebruiker;
import domein.interfacesDomein.IMedia;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import userinterface.sessie.IDetails;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.sql.SQLOutput;
import java.util.Optional;

public class BeherenMediaController extends AnchorPane implements IDetails {
    private DomeinController domeinController;
    private ObservableList<IMedia> media;
    private IMedia huidigeMedia;
    private File file;

    @FXML
    private ImageView imgmedia;

    @FXML
    private AnchorPane ap;

    @FXML
    private TableView<IMedia> table;

    @FXML
    private TableColumn<IMedia, String> tblMediatype;

    @FXML
    private ChoiceBox<String> cbmedia;

    @FXML
    private TextArea txtUrl;

    @FXML
    private Label txtErrorUrl;

    @FXML
    private Pane pOnderaan;

    @FXML
    private Button btnNieuw;

    @FXML
    private Button btnverwijder;

    @FXML
    private Button uploaden;

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
        vulTable();

        uploaden.setOnAction(this::uploadNewFoto);
        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<IMedia>() {
            @Override
            public void changed(ObservableValue<? extends IMedia> observableValue, IMedia iMedia, IMedia t1) {
                btnNieuw.setDisable(false);
                imgmedia.setVisible(true);
                huidigeMedia = t1;
                txtErrorUrl.setVisible(false);
                vulDetails();
            }
        });
        btnNieuw.setOnAction(this::veldenLeegmaaken);
        btnverwijder.setOnAction(this::verwijderMedia);
        comboBoxEventListener();
    }

    private void veldenLeegmaaken(ActionEvent actionEvent) {
        uploaden.setDisable(true);
        txtUrl.setText("");
        txtUrl.setEditable(true);
        cbmedia.setDisable(false);
        imgmedia.setVisible(false);
        cbmedia.getSelectionModel().select(1);
        btnNieuw.setOnAction(this::nieuweMediaFoto);
    }

    public void vulAfbeeldingIn(IMedia huidigeMedia){
        BufferedImage bimage = null;
        try {
            bimage = huidigeMedia.getAfbeeding();
        } catch (Exception ignored) {
        }

        if (bimage != null) {
            Image image = SwingFXUtils.toFXImage(bimage, null);
            imgmedia.setImage(image);
        }else{
            Image image = new Image("storage/profielfotos/profielfoto.png");
            imgmedia.setImage(image);
        }
    }

    private void uploadNewFoto(ActionEvent event) {
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
        vulTable();
    }

    public void vulTable(){
        table.getColumns().clear();
        table.setPlaceholder(new Label("Er is geen media voor deze sessie"));
        tblMediatype.setCellValueFactory(new PropertyValueFactory<>("type"));
        media = FXCollections.observableArrayList(domeinController.geefMediaVanHuidigeSessie());
        table.setItems(media);
        table.getColumns().addAll(tblMediatype);
        table.getSelectionModel().select(0);
        huidigeMedia = table.getSelectionModel().getSelectedItem();
        if(!domeinController.geefMediaVanHuidigeSessie().isEmpty())
            vulDetails();
    }

    private void vulDetails() {
        cbmedia.setItems(FXCollections.observableArrayList(domeinController.geefMediaTypes()));
        try{
            cbmedia.setValue(huidigeMedia.getTypeString());
            txtUrl.setText(huidigeMedia.getUrl());
        }catch(Exception e){

        }
        vulAfbeeldingIn(huidigeMedia);
    }

    private void nieuweMediaFoto(ActionEvent actionEvent) {
        uploaden.setDisable(false);
        if(cbmedia.getSelectionModel().selectedItemProperty().getValue() == "FOTO") {
            Stage stage = new Stage();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Media Toevoegen");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.gif", "*.jpg"));
            file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                try {
                    BufferedImage image = ImageIO.read(file);
                    domeinController.maakNieuweMedia(image);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            if(!txtUrl.getText().isEmpty())
                domeinController.maakNieuweMedia(domeinController.geefHuidigeISessie(), domeinController.geefHuidigeIGebruiker(), "URL", txtUrl.getText());
            else
                txtErrorUrl.setVisible(true);
        }
        //vulTable();
    }

    private void comboBoxEventListener() {
        cbmedia.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(t1 != null){
                    switch (t1) {
                        case "URL":
                            txtUrl.setDisable(false);
                            txtUrl.setVisible(true);
                            if(!cbmedia.isDisable())
                                txtUrl.setEditable(true);
                            break;
                        case "FOTO":
                            txtUrl.setEditable(false);
                            txtUrl.setDisable(true);
                            txtUrl.setVisible(false);
                            break;
                        case "ONBEKEND":
                            break;
                    }
                }
            }
        });
    }

    /*private void nieuweMedia(ActionEvent actionEvent){
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Media Toevoegen");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.gif", "*.jpg"));
        file = fileChooser.showOpenDialog(stage);
        try{
            BufferedImage image = ImageIO.read(file);
            domeinController.voegMediaToe(domeinController.geefHuidigeISessie(), domeinController.geefHuidigeIGebruiker(), cbmedia.getValue(), image, file.getName());
        } catch (IOException e) {
            System.err.println("Kan de afbeelding niet laden");
        }
    }*/



    private void verwijderMedia(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Verwijderen");
        alert.setHeaderText(null);
        alert.setContentText("Ben je zeker dat je deze Media wilt verwijderen?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            domeinController.verwijderMedia(table.getSelectionModel().getSelectedItem());
        }
    }

    @Override
    public void update() {
        vulTable();
    }
}
