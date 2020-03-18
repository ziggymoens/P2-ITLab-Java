package domein.interfacesDomein;

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
}