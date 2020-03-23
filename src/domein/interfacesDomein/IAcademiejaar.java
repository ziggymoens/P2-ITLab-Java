package domein.interfacesDomein;

import java.time.LocalDate;
import java.util.List;

public interface IAcademiejaar {
    int getAcademiejaar();

    List<ISessie> getSessiesO();

    LocalDate getStart();

    LocalDate getEind();

    String getAcademiejaarString();
    String getStartString();
    String getEindString();
    int getAantal ();
}
