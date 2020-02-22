package domein.interfacesDomein;

import domein.domeinklassen.Aankondiging;
import domein.domeinklassen.Feedback;
import domein.domeinklassen.Inschrijving;
import domein.domeinklassen.Media;
import domein.enums.Gebruikersprofielen;
import domein.enums.Gebruikersstatus;

import java.util.List;

public interface IGebruiker {
    IMedia getIProfielfoto();

    String getNaam();

    String getGebruikersnaam();

    Gebruikersstatus getStatus();

    Gebruikersprofielen getGebruikersprofiel();

    List<IFeedback> getIFeedbackList();

    List<IAankondiging> getIAankondigingList();

    List<IInschrijving> getIInschrijvingList();

    List<IMedia> getIMediaList();
}
