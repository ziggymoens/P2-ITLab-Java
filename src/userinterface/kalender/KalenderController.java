package userinterface.kalender;

import domein.DomeinController;
import domein.interfacesDomein.ISessie;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import net.bytebuddy.asm.Advice;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class KalenderController extends AnchorPane {
    private final DomeinController domeinController;
    private int GRIDPANE_COL = 7;
    private int GRIDPANE_ROW = 7;
    @FXML
    private GridPane gridpane;
    private String[] ms = {"januari", "februari", "maart", "april", "mei", "juni", "juli", "augustus", "september", "oktober", "november", "december"};
    private List<String> maanden;
    private Maand maand;

    public KalenderController(DomeinController domeinController) {
        this.domeinController = domeinController;
        maanden = Arrays.asList(ms);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Kalender.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        maand = new Maand(LocalDate.now().getMonthValue()+1);
        initKalender();
    }

    private void initKalender() {
        VBox vBox;
        int t = 1;
        for (int i = 1; i < GRIDPANE_ROW; i++) {
                /*hBox = new HBox();

                CharSequence cs = String.format("%d-%02d-%02d", LocalDate.now().getYear(), maand.getNummer(), t);
                LocalDate date = LocalDate.parse(cs);
                List<ISessie> sessies = domeinController.geefSessiesOpDag(date);
                ListView<ISessie> listView = new ListView<>();
                listView.setItems(FXCollections.observableArrayList(sessies));
                listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ISessie>() {
                    @Override
                    public void changed(ObservableValue<? extends ISessie> observableValue, ISessie sessie, ISessie t1) {
                        Scene scene = new Scene(new BorderPane(new Label(t1.toString())));
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.show();
                    }
                });

                 */
            //hBox.getChildren().addAll(new Label(String.format("%d %s", t, maand.afkorting())), listView);
            if (i == 1) {
                for (int j = maand.startDag(); j < GRIDPANE_COL; j++) {
                    if (t <= maand.aantalDagen()) {
                        vBox = new VBox();
                        vBox.getChildren().add(new Label(String.format("%d %s", t, maand.afkorting())));
                        CharSequence cs = String.format("%d-%02d-%02d", LocalDate.now().getYear(), maand.getNummer(), t);
                        LocalDate date = LocalDate.parse(cs);
                        List<ISessie> sessies = domeinController.geefSessiesOpDag(date);
                        for (ISessie sessie: sessies){
                            vBox.getChildren().add(new Label(sessie.toString()));
                        }
                        gridpane.add(vBox, j, i+1);
                        t++;
                    }
                }
            } else {
                for (int j = 0; j < GRIDPANE_COL; j++) {
                    if (t <= maand.aantalDagen()) {
                        vBox = new VBox();
                        Label label = new Label(String.format("%d %s", t, maand.afkorting()));
                        vBox.getChildren().add(label);
                        CharSequence cs = String.format("%d-%02d-%02d", LocalDate.now().getYear(), maand.getNummer(), t);
                        LocalDate date = LocalDate.parse(cs);
                        List<ISessie> sessies = domeinController.geefSessiesOpDag(date);
                        for (ISessie sessie: sessies){
                            vBox.getChildren().add(new Label(String.format("%s%n%s", sessie.getSessieId(), sessie.getTitel())));
                        }
                        gridpane.add(vBox, j, i+1);
                        t++;
                    }
                }
            }
        }
    }
}

