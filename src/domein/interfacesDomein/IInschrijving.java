package domein.interfacesDomein;

import java.time.LocalDateTime;

public interface IInschrijving {
    LocalDateTime getInschrijvingsdatum();

    boolean isStatusAanwezigheid();

    int getInschrijvingsId();

    IGebruiker getIGebruiker();
}
