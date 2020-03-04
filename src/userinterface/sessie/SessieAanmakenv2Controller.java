package userinterface.sessie;
import domein.DomeinController;
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
import userinterface.main.MainScreenController;

import java.io.IOException;
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
        if(verantwoordelijke.getText().isBlank()){
            foutVerantwoordelijke.setText("Verkeerde input");
        }
        if(naamGast.getText().isBlank()){
            foutGast.setText("Verkeerde input");
        }
        if(titel.getText().isBlank()){
            foutTitel.setText("Verkeerde input");
        }
        if(start.getText().isBlank()){
            foutStart.setText("Verkeerde input");
        }
        if(eind.getText().isBlank()){
            foutEind.setText("Verkeerde input");
        }
        if(maxPlaatsen.getText().isBlank()){
            foutMax.setText("Verkeerde input");
        } else{
            nieuweSessie.add(verantwoordelijke.getText());
            nieuweSessie.add(naamGast.getText());
            nieuweSessie.add(titel.getText());
            nieuweSessie.add(start.getText());
            nieuweSessie.add(eind.getText());
            nieuweSessie.add(maxPlaatsen.getText());
            domeinController.maakSessieAan(nieuweSessie);
            mainScreenController.getTabPane().getTabs().remove(mainScreenController.getTabPane().getSelectionModel().getSelectedIndex());
        }

    }

    private void cancel(ActionEvent actionEvent) {
        mainScreenController.getTabPane().getTabs().remove(mainScreenController.getTabPane().getSelectionModel().getSelectedIndex());
    }



}
