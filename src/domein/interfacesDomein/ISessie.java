package domein.interfacesDomein;

import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ISessie {
    String getTitel();

    String getNaamGastspreker();

    LocalDateTime getStartSessie();

    LocalDateTime getEindeSessie();

    int getMaximumAantalPlaatsen();

    ObservableList<IMedia> getIMediaBijSessie();

    ObservableList<IInschrijving> getIIngeschrevenGebruikers();

    ObservableList<IAankondiging> getIAankondigingenSessie();

    ObservableList<IFeedback> getIFeedbackSessie();

    ILokaal getLokaal();

    IGebruiker getVerantwoordelijke();

    boolean isGeopend();

    String getSessieId();

    //endregion
    Map<String, Object> gegevensDetails();

    int getAantalAanwezigen();

    int getBeschikbarePlaatsen();
}