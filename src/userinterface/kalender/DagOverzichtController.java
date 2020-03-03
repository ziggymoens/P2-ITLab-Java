package userinterface.kalender;

import domein.DomeinController;
import domein.interfacesDomein.ISessie;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import userinterface.main.MainScreenController;
import userinterface.sessieBeheren.SessieBewerkenController;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class DagOverzichtController extends AnchorPane {
    private DomeinController domeinController;
    private List<ISessie> sessies;
    private MainScreenController msc;

    @FXML
    private GridPane gridPane;

    public DagOverzichtController(DomeinController domeinController, List<ISessie> iSessies, MainScreenController msc) {
        this.domeinController = domeinController;
        this.sessies = iSessies;
        this.msc = msc;
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
                    bp.setCenter(new SessieBewerkenController(sessie, domeinController, null));
                    tab.setContent(bp);
                    tab.setText(sessie.getTitel());
                    msc.addTab(new Tab());
                });
                hBox.getChildren().add(button);
            }
            gridPane.add(hBox, i, 0);
        }
    }
}
