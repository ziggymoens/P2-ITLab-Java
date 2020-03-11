package userinterface.ongebruikt.sessie;

import domein.controllers.DomeinController;
import domein.interfacesDomein.ISessie;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import userinterface.ongebruikt.main.MainScreenController;

import java.io.IOException;
import java.util.List;

public class SessieTableViewController extends BorderPane {
    private DomeinController domeinController;
    private ObservableList<ISessie> sessies;
    private MainScreenController mainScreenController;

    @FXML
    private Button nieuweSessie, verwijderSessie, bewerkSessie, btnzoek;
    @FXML
    private TableView table;
    @FXML
    private TableColumn<ISessie, String> titel;
    @FXML
    private TableColumn<ISessie, String> startSessie;
    @FXML
    private TableColumn<ISessie, String> eindSessie;
    @FXML
    private TableColumn<ISessie, String> lastColumn;
    @FXML
    private ChoiceBox<String> choiceBoxKalenderJaar;
    @FXML
    private CheckBox nietGeopend, geopend, open, aalst, gent;
    @FXML
    private TextField zoeken;

    public SessieTableViewController(DomeinController domeinController, MainScreenController mainScreenController) {
        this.domeinController = domeinController;
        this.mainScreenController = mainScreenController;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SessieTableView.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

        vulTable(this.domeinController.geefISessiesHuidigeKalender());

        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ISessie>() {
            @Override
            public void changed(ObservableValue<? extends ISessie> observableValue, ISessie iSessie, ISessie t1) {
                domeinController.setHuidigeISessie(t1);
            }
        });

        nietGeopend.setOnAction(this::filterNietGeopend);
        geopend.setOnAction(this::filterGeopend);
        open.setOnAction(this::filterOpen);
        aalst.setOnAction(this::filterAalst);
        gent.setOnAction(this::filterGent);

        nieuweSessie.setOnAction(this::nieuweSessie);
        verwijderSessie.setOnAction(this::verwijderSessie);
        bewerkSessie.setOnAction(this::bewerkSessie);
        btnzoek.setOnAction(this::zoek);

    }

    public void vulTable(List<ISessie> isessies) {
        table.getColumns().clear();

        titel.setCellValueFactory(new PropertyValueFactory<>("titel"));
        startSessie.setCellValueFactory(new PropertyValueFactory<>("startSessie"));
        eindSessie.setCellValueFactory(new PropertyValueFactory<>("eindeSessie"));
        lastColumn.setCellValueFactory(new PropertyValueFactory<>("maximumAantalPlaatsen"));

        if (domeinController.geefIGebruiker().getGebruikersprofiel().toString().equals("VERANTWOORDELIJKE")) {
            sessies = FXCollections.observableArrayList(domeinController.geefISessiesVanGebruiker(isessies));
        } else {
            sessies = FXCollections.observableArrayList(isessies);
        }
        //System.out.println(sessies);

        table.setItems(sessies);
        table.getColumns().addAll(titel, startSessie, eindSessie, lastColumn);
        if (!table.getItems().isEmpty() || table.getItems() != null) {
            table.getSelectionModel().select(0);
            domeinController.setHuidigeISessie(sessies.get(0));
        }
    }

    private void filterGent(ActionEvent actionEvent) {
        if (gent.isSelected()) {
            aalst.setDisable(true);
            vulTable(domeinController.geefISessiesGent());
        } else if (!gent.isSelected()) {
            aalst.setDisable(false);
            vulTable(this.domeinController.geefISessiesHuidigeKalender());
        }
    }

    private void filterAalst(ActionEvent actionEvent) {
        if (aalst.isSelected()) {
            gent.setDisable(true);
            vulTable(this.domeinController.geefISessiesAalst());
        } else {
            gent.setDisable(false);
            vulTable(this.domeinController.geefISessiesHuidigeKalender());
        }
    }

    private void filterOpen(ActionEvent actionEvent) {
        if (open.isSelected()) {
            geopend.setDisable(true);
            nietGeopend.setDisable(true);
            vulTable(this.domeinController.geefISessiesHuidigeKalender());
        } else if (!open.isSelected()) {
            geopend.setDisable(false);
            nietGeopend.setDisable(false);
            vulTable(this.domeinController.geefISessiesHuidigeKalender());
        }
    }

    private void filterGeopend(ActionEvent actionEvent) {
        if (geopend.isSelected()) {
            nietGeopend.setDisable(true);
            open.setDisable(true);
            vulTable(this.domeinController.geefISessiesHuidigeKalender());
        } else if (!geopend.isSelected()) {
            nietGeopend.setDisable(false);
            open.setDisable(false);
            vulTable(this.domeinController.geefISessiesHuidigeKalender());
        }
    }

    private void filterNietGeopend(ActionEvent actionEvent) {
        if (nietGeopend.isSelected()) {
            geopend.setDisable(true);
            open.setDisable(true);
            vulTable(this.domeinController.geefISessiesHuidigeKalender());
        } else if (!nietGeopend.isSelected()) {
            geopend.setDisable(false);
            open.setDisable(false);
            vulTable(this.domeinController.geefISessiesHuidigeKalender());
        }
    }

    private void zoek(ActionEvent actionEvent) {
    }

    private void bewerkSessie(ActionEvent actionEvent) {
        Tab tab = new Tab("Bewerk Sessie");
        tab.setClosable(true);
        tab.setContent(new SessieBewerkenController((ISessie) table.getSelectionModel().getSelectedItem(), domeinController, mainScreenController));
        mainScreenController.addTab(tab);
        mainScreenController.getTabPane().getSelectionModel().select(tab);
    }

    private void nieuweSessie(ActionEvent actionEvent) {
        domeinController.setHuidigeISessie(null);
        Tab tab = new Tab("Aanmaken Sessie");
        tab.setClosable(true);
        tab.setContent(new SessieAanmakenv2Controller(domeinController, mainScreenController));
        mainScreenController.addTab(tab);
        mainScreenController.getTabPane().getSelectionModel().select(tab);

    }

    private void verwijderSessie(ActionEvent actionEvent) {
        domeinController.verwijderSessie(domeinController.geefHuidigeISessie());
    }


  /*   private void observable() {

       choiceBoxKalenderJaar.setItems(FXCollections.observableArrayList(domeinController.geefAcademiejaren()));
        choiceBoxKalenderJaar.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                sessies = FXCollections.observableArrayList(domeinController.geefISessiesAcademiejaar(Integer.valueOf(t1)));

           }
        });
        //choiceBoxKalenderJaar.setValue(choiceBoxKalenderJaar.getItems().get(0));

    }*/
}
