package userinterface.sessie.media;

import domein.controllers.DomeinController;
import domein.interfacesDomein.IFeedback;
import domein.interfacesDomein.IMedia;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class BeherenMediaController extends AnchorPane {
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
    private Button btnWijzig;

    @FXML
    private Button btnOpslaan;

    @FXML
    private Button btnNieuw;

    @FXML
    private Button btnverwijder;

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
        btnOpslaan.setDisable(true);
        btnOpslaan.setVisible(false);
        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<IMedia>() {
            @Override
            public void changed(ObservableValue<? extends IMedia> observableValue, IMedia iMedia, IMedia t1) {
                btnNieuw.setDisable(false);
                btnOpslaan.setDisable(true);
                btnOpslaan.setVisible(false);
                btnWijzig.setDisable(false);
                btnWijzig.setVisible(true);
                huidigeMedia = t1;
                vulDetails();
            }
        });
        btnNieuw.setOnAction(this::nieuweMedia);
        btnverwijder.setOnAction(this::verwijderMedia);
    }




    public void vulTable(){
        table.getColumns().clear();

        tblMediatype.setCellValueFactory(new PropertyValueFactory<>("type"));
        media = FXCollections.observableArrayList(domeinController.geefMediaVanHuidigeSessie());
        table.setItems(media);
        table.getColumns().addAll(tblMediatype);
        table.getSelectionModel().select(0);
        huidigeMedia = table.getSelectionModel().getSelectedItem();
        vulDetails();
    }

    private void vulDetails() {
        cbmedia.setItems(FXCollections.observableArrayList(domeinController.geefMediaTypes()));
        cbmedia.setValue(huidigeMedia.getTypeString());
        txtUrl.setText(huidigeMedia.getLocatie());
    }

    private void nieuweMedia(ActionEvent actionEvent) {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Media Toevoegen");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.gif", "*.jpg"));
        file = fileChooser.showOpenDialog(stage);
        System.out.println(file);
        /*if (file != null) {
            filePath.setText(file.getAbsolutePath());
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
            openFile.setDisable(true);
        }*/
    }

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
}
