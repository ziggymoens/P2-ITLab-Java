package domein.interfacesDomein;

import java.time.LocalDate;

public interface IFeedback {
    String getTekst();

    String getFeedbackId();

    IGebruiker getIGebruiker();

    LocalDate getDate();

    boolean getVerwijderd();

    String toString_Compleet();
}
