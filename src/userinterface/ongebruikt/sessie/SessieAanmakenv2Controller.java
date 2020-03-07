package userinterface.ongebruikt.sessie;
import domein.controllers.DomeinController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import userinterface.ongebruikt.main.MainScreenController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class SessieAanmakenv2Controller extends BorderPane {
    private DomeinController domeinController;
    private List<String> nieuweSessie;
    private MainScreenController mainScreenController;

    @FXML
    private Label foutTitel, foutGast, foutMax, foutStart, foutEind, foutVerantwoordelijke;
    @FXML
    private TextField titel, naamGast, maxPlaatsen, start, eind, verantwoordelijke;
    @FXML
    private ChoiceBox<String> lokaal;
    @FXML
    private Button save, cancel;

    public SessieAanmakenv2Controller(DomeinController domeinController, MainScreenController msc) {
        this.mainScreenController = msc;
        this.domeinController = domeinController;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SessieAanmakenv2.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

        verantwoordelijke.setEditable(true);
        naamGast.setEditable(true);
        titel.setEditable(true);
        start.setEditable(true);
        eind.setEditable(true);
        maxPlaatsen.setEditable(true);

        ObservableList<String> lokaalCodes = FXCollections.observableArrayList(domeinController.geefILokalen().stream().map(k -> k.getLokaalCode()).collect(Collectors.toList()));
        lokaal.setItems(lokaalCodes);
        lokaal.setValue(lokaalCodes.stream().filter(e -> e.equals("IT-Lab")).findFirst().orElse(lokaalCodes.get(0)));

        save.setOnAction(this::save);
        cancel.setOnAction(this::cancel);
    }

    private void save (ActionEvent actionEvent){
        if(testOpFouten() >= 0) {
            domeinController.maakSessieAan(nieuweSessie);
            mainScreenController.getTabPane().getTabs().remove(mainScreenController.getTabPane().getSelectionModel().getSelectedIndex());
        }
    }

    private void cancel(ActionEvent actionEvent) {
        mainScreenController.getTabPane().getTabs().remove(mainScreenController.getTabPane().getSelectionModel().getSelectedIndex());
    }

    private int testOpFouten() {
        int tempint = 0;
        nieuweSessie = new ArrayList<>();

        if(verantwoordelijke.getText().isBlank()){
            tempint--;
            foutVerantwoordelijke.setText("Verkeerde input");
        } else{
            foutVerantwoordelijke.setText("");
            nieuweSessie.add(verantwoordelijke.getText());
        }
        if(naamGast.getText().isBlank()){
            tempint--;
            foutGast.setText("Verkeerde input");
        } else{
            foutGast.setText("");
            nieuweSessie.add(naamGast.getText());
        }
        if(titel.getText().isBlank()){
            tempint--;
            foutTitel.setText("Verkeerde input");
        } else{
            foutTitel.setText("");
            nieuweSessie.add(titel.getText());
        }
        if(start.getText().isBlank()){
            tempint--;
            foutStart.setText("Verkeerde input");
        } else{
            foutStart.setText("");
            nieuweSessie.add(start.getText());
        }
        if(eind.getText().isBlank()){
            tempint--;
            foutEind.setText("Verkeerde input");
        } else{
            foutEind.setText("");
            nieuweSessie.add(eind.getText());
        }
        if(maxPlaatsen.getText().isBlank()){
            tempint--;
            foutMax.setText("Verkeerde input");
        } else{
            foutMax.setText("");
            nieuweSessie.add(maxPlaatsen.getText());
        }
        return tempint;
    }




}
