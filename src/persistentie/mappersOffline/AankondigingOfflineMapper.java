package persistentie.mappersOffline;

import domein.Aankondiging;
import domein.Gebruiker;
import domein.Sessie;
import exceptions.persistentie.offline.GebruikerOfflineMapperException;
import org.mockito.exceptions.misusing.UnnecessaryStubbingException;
import persistentie.PersistentieController;
import persistentie.mappersAbs.AankondigingMapperAb;

import java.io.*;
import java.time.LocalDateTime;
import java.util.List;

public class AankondigingOfflineMapper extends AankondigingMapperAb {

    private final File aankondigingenOffline = new File("src/offlineData/initData/Aankondiging");

    public AankondigingOfflineMapper() {
        super();
    }

    @Override
    public void initData() {
        if (Initialiseren.isInitialiseren()) {
            maakAankondigingen();
        } else {
            leesAankondigingen();
        }
    }

    private void maakAankondigingen() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(aankondigingenOffline));
            String line;
            while ((line = br.readLine()) != null) {
                String[] aan = line.split(";");
                Gebruiker gebruiker = persistentieController.geefGebruikerMetCode(aan[3]);
                Sessie sessie = persistentieController.geefSessieMetId(aan[1]);
                aankondigingList.add(new Aankondiging(aan[0], sessie, LocalDateTime.parse(aan[2]), gebruiker, aan[4]));
            }
        } catch (IOException e) {
            throw new GebruikerOfflineMapperException();
        }
        schrijfAankondigingen();
    }

    private void leesAankondigingen() {
        try {
            FileInputStream fis = new FileInputStream("src/offlineData/serieel/Aankondiging.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            while (true) {
                Aankondiging aankondiging = (Aankondiging) ois.readObject();
                aankondigingList.add(aankondiging);
            }
        } catch (EOFException ignored) {
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void schrijfAankondigingen() {
        try {
            FileOutputStream fos = new FileOutputStream("src/offlineData/serieel/Aankondiging.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for (Aankondiging aankondiging : aankondigingList) {
                oos.writeObject(aankondiging);
            }
        } catch (IOException e) {
            throw new GebruikerOfflineMapperException();
        }
    }

    @Override
    public void setPersistentieController(PersistentieController persistentieController) {
        this.persistentieController = persistentieController;
    }

    @Override
    public List<Aankondiging> getAankodigingen() {
        return aankondigingList;
    }

    @Override
    public void voegAankondigingToe(Aankondiging aankondiging) {
        aankondigingList.add(aankondiging);
    }

    @Override
    public void verwijderAankondiging(Aankondiging aankondiging) {
        aankondigingList.remove(aankondiging);
    }

    @Override
    public void updateAankondiging(Aankondiging aankondiging) {
        throw new UnsupportedOperationException();
    }
}
