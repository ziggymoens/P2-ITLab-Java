package domein.interfacesDomein;

import java.awt.image.BufferedImage;
import java.time.LocalDate;

public interface IGebruiker {
    String getNaam();

    String getGebruikersnaam();

    String getStatus();

    String getGebruikersprofiel();

    BufferedImage getProfielfoto();

    LocalDate getLaatstIngelogd();

    String getWachtwoord();

    //List<IFeedback> getIFeedbackList();

    //List<IAankondiging> getIAankondigingList();

    //List<IInschrijving> getIInschrijvingList();

    //List<IMedia> getIMediaList();
}
