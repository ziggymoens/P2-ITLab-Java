package userinterface.kalender;

import domein.DomeinController;
import domein.interfacesDomein.ISessie;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import userinterface.main.MainScreenController;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class KalenderController extends AnchorPane {
    private DomeinController domeinController;
    private MainScreenController msc;
    private int GRIDPANE_COL = 7;
    private int GRIDPANE_ROW = 7;
    @FXML
    private GridPane gridpane;
    @FXML
    private Label naam;
    @FXML
    private Button vorig, volgend, vorigJaar, volgendJaar;
    private KalenderGegevens kalenderGegevens;

    public KalenderController(DomeinController domeinController, MainScreenController mainScreenController) {
        this.msc = mainScreenController;
        this.domeinController = domeinController;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Kalender.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        kalenderGegevens = new KalenderGegevens(LocalDate.now().getMonthValue(), LocalDate.now().getYear());
        startKal();
    }

    public KalenderController(DomeinController domeinController, MainScreenController msc, KalenderGegevens kalenderGegevens) {
        this.domeinController = domeinController;
        this.msc = msc;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Kalender.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        this.kalenderGegevens = kalenderGegevens;
        startKal();
    }

    private void startKal() {
        naam.setText(String.format("%s %s", kalenderGegevens.geefNaamMaand(), kalenderGegevens.geefJaar()));
        vorig.setOnAction(this::vorigeMaand);
        volgend.setOnAction(this::volgendeMaand);
        vorigJaar.setOnAction(this::vorigJaar);
        volgendJaar.setOnAction(this::volgendJaar);

        if (kalenderGegevens.volgendeMaand().geefJaar() > KalenderGegevens.EIND) {
            volgend.setDisable(true);
        }
        if (kalenderGegevens.vorigeMaand().geefJaar() < KalenderGegevens.BEGIN) {
            vorig.setDisable(true);
        }
        if (kalenderGegevens.volgendJaar().geefJaar() > KalenderGegevens.EIND) {
            volgendJaar.setDisable(true);
        }
        if (kalenderGegevens.vorigJaar().geefJaar() < KalenderGegevens.BEGIN) {
            vorigJaar.setDisable(true);
        }
        initKalender();
    }

    private void volgendJaar(ActionEvent event) {
        KalenderGegevens kg = kalenderGegevens.volgendJaar();
        msc.geefMainBP().setLeft(
                new KalenderController(domeinController, msc, kg));
    }

    private void vorigJaar(ActionEvent event) {
        KalenderGegevens kg = kalenderGegevens.vorigJaar();
        msc.geefMainBP().setLeft(new KalenderController(domeinController, msc, kg));
    }


    private void vorigeMaand(ActionEvent event) {
        KalenderGegevens kg = kalenderGegevens.vorigeMaand();
        msc.geefMainBP().setLeft(new KalenderController(domeinController, msc, kg));
    }

    private void volgendeMaand(ActionEvent event) {
        KalenderGegevens kg = kalenderGegevens.volgendeMaand();
        msc.geefMainBP().setLeft(new KalenderController(domeinController, msc, kg));
    }


    private void initKalender() {
        VBox vBox;
        int t = 1;
        for (int i = 1; i < GRIDPANE_ROW; i++) {
            if (i == 1) {
                for (int j = kalenderGegevens.startDag(); j < GRIDPANE_COL; j++) {
                    if (t <= kalenderGegevens.aantalDagen()) {
                        vBox = new VBox();
                        vBox.getChildren().add(new Label(String.format("%d %s", t, kalenderGegevens.afkorting())));
                        CharSequence cs = String.format("%d-%02d-%02d", kalenderGegevens.geefJaar(), kalenderGegevens.geefNummerMaand(), t);
                        LocalDate date = LocalDate.parse(cs);
                        List<ISessie> sessies = domeinController.geefSessiesOpDag(date);
                        if (sessies.size() != 0) {
                            Button button = new Button(String.valueOf(sessies.size()));
                            button.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    msc.geefMainBP().setRight(new DagOverzichtController(domeinController, sessies, msc));
                                }
                            });
                            vBox.getChildren().add(button);
                        }
                        if (LocalDate.now().getDayOfYear() == date.getDayOfYear()) {
                            vBox.setStyle("-fx-background-color: #FFCC99;");
                        }
                        gridpane.add(vBox, j, i);
                        t++;
                    }
                }

            } else {
                for (int j = 0; j < GRIDPANE_COL; j++) {
                    if (t <= kalenderGegevens.aantalDagen()) {
                        vBox = new VBox();
                        Label label = new Label(String.format("%d %s", t, kalenderGegevens.afkorting()));
                        vBox.getChildren().add(label);
                        CharSequence cs = String.format("%d-%02d-%02d", kalenderGegevens.geefJaar(), kalenderGegevens.geefNummerMaand(), t);
                        LocalDate date = LocalDate.parse(cs);
                        List<ISessie> sessies = domeinController.geefSessiesOpDag(date);
                        if (sessies.size() != 0) {
                            Button button = new Button(String.valueOf(sessies.size()));
                            button.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    msc.geefMainBP().setRight(new DagOverzichtController(domeinController, sessies, msc));
                                }
                            });
                            vBox.getChildren().add(button);
                        }
                        if (LocalDate.now().getDayOfYear() == date.getDayOfYear()) {
                            vBox.setStyle("-fx-background-color: #FFCC99;");
                        }
                        gridpane.add(vBox, j, i);
                        t++;
                    }
                }
            }
        }
    }
}

/*
for (ISessie sessie : sessies) {
                            Button button = new Button(sessie.toString());
                            button.setOnAction(e -> {
                                Stage stage = new Stage();
                                Scene scene = new Scene(new SessieBewerkenController(sessie, domeinController));
                                stage.setScene(scene);
                                stage.show();
                            });
                            vBox.getChildren().add(button);
                        }
 */
