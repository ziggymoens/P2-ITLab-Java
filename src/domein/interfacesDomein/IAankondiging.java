package domein.interfacesDomein;

import domein.gebruiker.Gebruiker;

import java.time.LocalDateTime;

public interface IAankondiging {
    LocalDateTime getPublicatiedatum();

    Gebruiker getPublicist();

    String getInhoud();

    String getAankondigingsId();

    IHerinnering getIHerinnering();

    boolean isAutomatischeHerinnering();

    IGebruiker getIGebruiker();

    String toString_Compleet();

    boolean getVerwijderd();
}
