package domein;

import java.util.Date;
import java.util.List;

public class Sessie {
    private String titel;
    private String naamGastspreker;
    private Date startSessie;
    private Date eindeSessie;
    private int maximumAantalPlaatsen;
    private boolean automatischeHerinnering;
    private Herinnering herinnering;
    private List<Media> mediaBijSessie;
    private List<Inschrijving> ingeschrevenGebruikers;
    private List<Aankondiging> aankondigingenSessie;
    private List<Feedback> feedbackSessie;
    private Lokaal lokaal;
    private Gebruiker verantwoordelijke;
}
