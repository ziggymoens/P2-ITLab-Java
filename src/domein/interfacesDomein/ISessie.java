package domein.interfacesDomein;

import userinterface.main.IObserverSessie;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ISessie {
    String getTitel();

    String getNaamGastspreker();

    LocalDateTime getStartSessie();

    LocalDateTime getEindeSessie();

    int getMaximumAantalPlaatsen();

    List<IMedia> getIMediaBijSessie();

    List<IInschrijving> getIIngeschrevenGebruikers();

    List<IAankondiging> getIAankondigingenSessie();

    List<IFeedback> getIFeedbackSessie();

    ILokaal getLokaal();

    IGebruiker getVerantwoordelijke();

    boolean isGeopend();

    String getSessieId();

    Map<String, Object> gegevensDetails();

    int getAantalAanwezigen();

    int getBeschikbarePlaatsen();

    String toString_Kalender();

    void add(IObserverSessie observers);
    void remove(IObserverSessie observers);
    void notifyObservers();
}