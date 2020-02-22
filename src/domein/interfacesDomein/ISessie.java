package domein.interfacesDomein;

import domein.domeinklassen.*;

import java.time.LocalDateTime;
import java.util.List;

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

    int getSessieId();
}