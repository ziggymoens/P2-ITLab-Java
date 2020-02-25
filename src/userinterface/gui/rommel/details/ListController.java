package userinterface.gui.rommel.details;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.BorderPane;
import userinterface.gui.rommel.main.MainScreenController;

import java.io.IOException;

public class ListController<T> extends BorderPane {
    private DetailsController<T> detailsController;

    @FXML
    private ListView<T> listView;

    public ListController(MainScreenController mainScreenController) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("List.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        detailsController = new DetailsController<>();
        this.setRight(detailsController);
    }

    public void setItems(ObservableList<?> observableList){
        listView.setItems((ObservableList<T>)observableList);
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listView.setOnMouseClicked(e -> detailsController.geefDetails(listView));

    }

    @Override
    public Node getStyleableNode() {
        return null;
    }
}
