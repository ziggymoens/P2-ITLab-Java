package userinterface.kalender;

import domein.DomeinController;
import domein.interfacesDomein.ISessie;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.*;
import userinterface.MAIN.MainScreenController;
import userinterface.sessieBeheren.SessieBewerkenController;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class DagOverzichtController extends AnchorPane {
    private DomeinController domeinController;
    private List<ISessie> sessies;

    @FXML
    private GridPane gridPane;

    public DagOverzichtController(DomeinController domeinController, List<ISessie> iSessies) {
        this.domeinController = domeinController;
        this.sessies = iSessies;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DagOverzicht.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        initDag();
    }

    private void initDag() {
        for (int i = 0; i <= 23; i++) {
            HBox hBox = new HBox();
            int finalI = i;
            List<ISessie> sessiesH = sessies.stream().filter(s -> s.getStartSessie().getHour() == finalI).collect(Collectors.toList());
            for (ISessie sessie : sessiesH) {
                Button button = new Button(sessie.getTitel());
                button.setOnAction(b -> {
                    Tab tab = new Tab();
                    BorderPane bp = new BorderPane();
                    bp.setCenter(new SessieBewerkenController(sessie, domeinController));
                    tab.setContent(bp);
                    tab.setText(sessie.getTitel());
                    MainScreenController.addTab(new Tab());
                });
                hBox.getChildren().add(button);
            }
            gridPane.add(hBox, i, 0);
        }
    }
}
