package domein.interfacesDomein;

import java.time.LocalDateTime;

public interface IGebruiker {
    String getNaam();

    String getGebruikersnaam();

    String getStatus();

    String getGebruikersprofiel();

    byte[] getProfielfoto();

    LocalDateTime getLaatstIngelogd();

    String getWachtwoord();

    //List<IFeedback> getIFeedbackList();

    //List<IAankondiging> getIAankondigingList();

    //List<IInschrijving> getIInschrijvingList();

    //List<IMedia> getIMediaList();
}
