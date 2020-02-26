package domein.interfacesDomein;

import domein.domeinklassen.Gebruiker;
import domein.enums.Gebruikersprofielen;
import domein.enums.Gebruikersstatus;

import java.util.List;

public interface IGebruiker {
    String getNaam();

    String getGebruikersnaam();

    Gebruikersstatus getStatus();

    Gebruikersprofielen getGebruikersprofiel();

    String getProfielfoto();

    //List<IFeedback> getIFeedbackList();

    //List<IAankondiging> getIAankondigingList();

    //List<IInschrijving> getIInschrijvingList();

    //List<IMedia> getIMediaList();
}
