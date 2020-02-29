package domein.interfacesDomein;

import domein.enums.Gebruikersprofielen;
import domein.enums.Gebruikersstatus;

import java.time.LocalDateTime;

public interface IGebruiker {
    String getNaam();

    String getGebruikersnaam();

    Gebruikersstatus getStatus();

    Gebruikersprofielen getGebruikersprofiel();

    byte[] getProfielfoto();

    LocalDateTime getLaatstIngelogd();

    String getWachtwoord();

    //List<IFeedback> getIFeedbackList();

    //List<IAankondiging> getIAankondigingList();

    //List<IInschrijving> getIInschrijvingList();

    //List<IMedia> getIMediaList();
}
