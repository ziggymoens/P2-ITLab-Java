package userinterface.gui.rommel.details;

import domein.interfacesDomein.ISessie;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class DetailsController<T> extends BorderPane {
    private ObservableList<T> observableList;
    @FXML
    private VBox detailsVBox;
    @FXML
    private Button detailsEdit, detailsDelete;

    public DetailsController() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Details.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /*public void setObservableList(ObservableList<T> observableList) {
        this.observableList = observableList;
    }

     */

    public void geefDetails(ListView<T> listView) {
        T object = listView.getSelectionModel().getSelectedItems().stream().findFirst().orElse(null);
        ((ISessie)object).gegevensDetails().entrySet().stream().map(e -> {
            HBox hBox = new HBox();
            hBox.getChildren().add(new Label(e.getKey()));
            hBox.getChildren().add(new Label((String)e.getValue()));
            detailsVBox.getChildren().add(hBox);
            return null;
        });
    }
}
