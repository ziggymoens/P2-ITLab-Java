package domein.interfacesDomein;

import java.time.LocalDateTime;

public interface IInschrijving {
    LocalDateTime getInschrijvingsdatum();

    boolean isStatusAanwezigheid();

    String getInschrijvingsId();

    IGebruiker getIGebruiker();
}
