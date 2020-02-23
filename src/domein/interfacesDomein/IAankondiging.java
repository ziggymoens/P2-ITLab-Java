package domein.interfacesDomein;

import domein.domeinklassen.Gebruiker;

import java.time.LocalDateTime;

public interface IAankondiging {
    LocalDateTime getPublicatiedatum();

    Gebruiker getPublicist();

    String getInhoud();

    String getAankondigingsId();

    IHerinnering getIHerinnering();

    boolean isAutomatischeHerinnering();

    IGebruiker getIGebruiker();
}
