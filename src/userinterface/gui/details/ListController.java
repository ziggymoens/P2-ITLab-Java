package userinterface.gui.details;

import domein.DomeinController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import userinterface.gui.main.MainScreenController;

import java.io.IOException;

public class ListController<T> {
    private final DomeinController domeinController;
    private final MainScreenController mainScreenController;

    @FXML
    ListView<T> listView;

    private ListController(MainScreenController mainScreenController) {
        this.mainScreenController = mainScreenController;
        this.domeinController = mainScreenController.getDomeinController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("List.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public void setItems(ObservableList<T> observableList){
        listView.setItems(observableList);
    }
}
