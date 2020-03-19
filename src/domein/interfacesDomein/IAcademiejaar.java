package domein.interfacesDomein;

import domein.sessie.Sessie;

import java.time.LocalDate;
import java.util.List;

public interface IAcademiejaar {
    int getAcademiejaar();

    List<ISessie> getSessies();

    LocalDate getStart();

    LocalDate getEind();
}
