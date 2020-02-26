package userinterface.kalender;

import domein.DomeinController;
import domein.interfacesDomein.ISessie;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import net.bytebuddy.asm.Advice;
import org.w3c.dom.DOMError;
import userinterface.MAIN.MainScreenController;
import userinterface.sessieBeheren.InfoSessieController;
import userinterface.sessieBeheren.SessieBewerkenController;

import java.io.DataOutput;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class KalenderController extends AnchorPane {
    private DomeinController domeinController;
    private MainScreenController mainScreenController;
    private int GRIDPANE_COL = 7;
    private int GRIDPANE_ROW = 7;
    @FXML
    private GridPane gridpane;
    @FXML
    private Label naam;
    @FXML
    private Button vorig, volgend;
    private String[] ms = {"januari", "februari", "maart", "april", "mei", "juni", "juli", "augustus", "september", "oktober", "november", "december"};
    private List<String> maanden;
    private Maand maand;

    public KalenderController(DomeinController domeinController, MainScreenController mainScreenController) {
        this.mainScreenController = mainScreenController;
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
        maand = new Maand(LocalDate.now().getMonthValue());
        startKal();
    }

    public KalenderController(DomeinController domeinController,MainScreenController mainScreenController, int m){
        this.domeinController = domeinController;
        this.mainScreenController = mainScreenController;
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
        this.maand = new Maand(m);
        startKal();
    }

    private void startKal(){
        naam.setText(maanden.get(maand.getNummer() - 1));
        vorig.setOnAction(this::vorigeMaand);
        volgend.setOnAction(this::volgendeMaand);
        initKalender();
    }


    private void vorigeMaand(ActionEvent event) {
        int huidig = maand.getNummer();
        if (huidig == 1) {
            huidig = 12;
        } else {
            huidig --;
        }
        mainScreenController.setCenter(new KalenderController(domeinController, mainScreenController, huidig));
    }

    private void volgendeMaand(ActionEvent event) {
        int huidig = maand.getNummer();
        if (huidig == 12) {
            huidig = 1;
        } else {
            huidig++;
        }
        mainScreenController.setCenter(new KalenderController(domeinController, mainScreenController, huidig));
    }


    private void initKalender() {
        VBox vBox;
        int t = 1;
        for (int i = 1; i < GRIDPANE_ROW; i++) {
            if (i == 1) {
                for (int j = maand.startDag(); j < GRIDPANE_COL; j++) {
                    if (t <= maand.aantalDagen()) {
                        vBox = new VBox();
                        vBox.getChildren().add(new Label(String.format("%d %s", t, maand.afkorting())));
                        CharSequence cs = String.format("%d-%02d-%02d", LocalDate.now().getYear(), maand.getNummer(), t);
                        LocalDate date = LocalDate.parse(cs);
                        List<ISessie> sessies = domeinController.geefSessiesOpDag(date);
                        for (ISessie sessie : sessies) {
                            Button button = new Button(sessie.toString());
                            button.setOnAction(e -> {
                                Stage stage = new Stage();
                                Scene scene = new Scene(new InfoSessieController(sessie));
                                stage.setScene(scene);
                                stage.show();
                            });
                            vBox.getChildren().add(button);
                        }
                        gridpane.add(vBox, j, i + 1);
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
                        for (ISessie sessie : sessies) {
                            Button button = new Button(sessie.toString_Kalender());
                            button.setOnAction(e -> {
                                Stage stage = new Stage();
                                Scene scene = new Scene(new SessieBewerkenController(sessie, domeinController));
                                stage.setScene(scene);
                                stage.show();
                            });
                            vBox.getChildren().add(button);
                        }
                        gridpane.add(vBox, j, i + 1);
                        t++;
                    }
                }
            }
        }
    }
}

