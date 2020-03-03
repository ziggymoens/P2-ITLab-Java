package userinterface.MAIN;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.stream.Collectors;

public class OverzichtController<T> extends AnchorPane {

    @FXML
    private TableView<T> tableView;

    public OverzichtController() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Overzicht.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public void initTable(ObservableList<T> obj){
        System.out.println(obj.stream().map(s -> s.toString()).collect(Collectors.joining("\n")));
        Method[] methods  = obj.get(0).getClass().getMethods();
        for (Method method : methods){
            System.out.println(method.getName());
            tableView.getColumns().add(new TableColumn<>(method.getName()));
        }

        tableView.setItems(obj);
    }
}
