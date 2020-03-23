package domein.interfacesDomein;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public interface ISessie {
    String getTitel();

    String getNaamGastspreker();

    LocalDateTime getStartSessie();

    LocalDateTime getEindeSessie();

    LocalDate getDatum();

    String getDatumString();

    LocalTime getStartUur();

    LocalTime getEindeUur();

    int getMaximumAantalPlaatsen();

    List<IMedia> getIMediaBijSessie();

    List<IInschrijving> getIIngeschrevenGebruikers();

    List<IAankondiging> getIAankondigingenSessie();

    List<IFeedback> getIFeedbackSessie();

    ILokaal getLokaal();

    IGebruiker getVerantwoordelijke();

    String getSessieId();

    IAcademiejaar getAcademiejaar();

    String getBeschrijving();

    String getCurrentState();

    Map<String, Object> gegevensDetails();

    int getAantalAanwezigen();

    int getBeschikbarePlaatsen();

    String getStad();

    String toString_Kalender();
}