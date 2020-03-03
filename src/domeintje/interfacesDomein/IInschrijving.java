package domeintje.interfacesDomein;

import java.time.LocalDateTime;

public interface IInschrijving {
    LocalDateTime getInschrijvingsdatum();

    boolean isStatusAanwezigheid();

    String getInschrijvingsId();

    IGebruiker getIGebruiker();

    String toString_Compleet();

    String toString_Kort();
}
