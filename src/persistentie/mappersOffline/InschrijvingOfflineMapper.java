package persistentie.mappersOffline;

import domein.Inschrijving;
import domein.Gebruiker;
import domein.Sessie;
import exceptions.persistentie.InschrijvingPersistentieException;
import persistentie.PersistentieController;
import persistentie.mappers.InschrijvingMapper;

import java.io.*;
import java.time.LocalDateTime;
import java.util.List;

public class InschrijvingOfflineMapper extends InschrijvingMapper {

    private final File inschrijvingenOffline = new File("src/offlineData/initData/Inschrijving");

    public InschrijvingOfflineMapper() {
        super();
    }

    @Override
    public void initData() {
        if (Initialiseren.isInitialiseren()) {
            maakInschrijvingen();
        } else {
            leesInschrijvingen();
        }
    }

    private void maakInschrijvingen() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(inschrijvingenOffline));
            String line;
            while ((line = br.readLine()) != null) {
                String[] inschrijving = line.split(";");
                Gebruiker gebruiker = persistentieController.geefGebruikerMetCode(inschrijving[1]);
                Sessie sessie = persistentieController.geefSessieMetId(inschrijving[2]);
                inschrijvingList.add(new Inschrijving(inschrijving[0], gebruiker, sessie, LocalDateTime.parse(inschrijving[3]), inschrijving[4].equals("aanwezig")));
            }
        } catch (IOException e) {
            throw new InschrijvingPersistentieException("InschrijvingOfflineMapper");
        }
        schrijfInschrijvingen();
    }

    private void leesInschrijvingen() {
        try {
            FileInputStream fis = new FileInputStream("src/offlineData/serieel/Inschrijving.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            while (true) {
                Inschrijving inschrijving = (Inschrijving) ois.readObject();
                inschrijvingList.add(inschrijving);
            }
        } catch (EOFException ignored) {
        } catch (IOException | ClassNotFoundException e) {
            throw new InschrijvingPersistentieException("InschrijvingOfflineMapper");
        }
    }

    @Override
    public void schrijfInschrijvingen() {
        try {
            FileOutputStream fos = new FileOutputStream("src/offlineData/serieel/Inschrijving.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for (Inschrijving inschrijving : inschrijvingList) {
                oos.writeObject(inschrijving);
            }
        } catch (IOException e) {
            throw new InschrijvingPersistentieException("InschrijvingOfflineMapper");
        }
    }

    @Override
    public void setPersistentieController(PersistentieController persistentieController) {
        this.persistentieController = persistentieController;
    }

    @Override
    public List<Inschrijving> getAankodigingen() {
        return inschrijvingList;
    }

    @Override
    public void voegInschrijvingToe(Inschrijving inschrijving) {
        inschrijvingList.add(inschrijving);
    }

    @Override
    public void verwijderInschrijving(Inschrijving inschrijving) {
        inschrijvingList.remove(inschrijving);
    }

    @Override
    public void updateInschrijving(Inschrijving inschrijving) {
        throw new UnsupportedOperationException();
    }
}
