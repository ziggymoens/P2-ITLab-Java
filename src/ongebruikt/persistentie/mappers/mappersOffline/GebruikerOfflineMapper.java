package ongebruikt.persistentie.mappers.mappersOffline;

import domein.Gebruiker;
import exceptions.persistentie.GebruikerPersistentieException;
import ongebruikt.persistentie.mappers.GebruikerMapper;

import java.io.*;
import java.util.Set;

public class GebruikerOfflineMapper extends GebruikerMapper {

    private final File gebruikersoffline = new File("src/ongebruikt.offlineData/initData/Gebruiker");

    public GebruikerOfflineMapper() {
        super();
    }

    @Override
    public void initData() {
        if (Initialiseren.isInitialiseren()) {
            maakGebruikers();
        } else {
            leesGebruikers();
        }
    }

    private void maakGebruikers() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(gebruikersoffline));
            String line;
            while ((line = br.readLine()) != null) {
                String[] geb = line.split(";");
                String profielfoto = null;
                if (geb.length == 5) {
                    profielfoto = geb[4];
                }
                gebruikerSet.add(new Gebruiker(geb[0], geb[1], geb[2], geb[3], profielfoto));
            }
        } catch (IOException e) {
            throw new GebruikerPersistentieException("GebruikerOfflineMapper");
        }
        schrijfGebruikers();
    }

    public void leesGebruikers() {
        try {
            FileInputStream fis = new FileInputStream("src/ongebruikt.offlineData/serieel/Gebruiker.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            while (true) {
                Gebruiker gebruiker = (Gebruiker) ois.readObject();
                gebruikerSet.add(gebruiker);
            }
        } catch (EOFException ignored) {
        } catch (IOException | ClassNotFoundException e) {
            throw new GebruikerPersistentieException("GebruikerOfflineMapper");
        }
    }

    @Override
    public void schrijfGebruikers() {
        try {
            FileOutputStream fos = new FileOutputStream("src/ongebruikt.offlineData/serieel/Gebruiker.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for (Gebruiker g : gebruikerSet) {
                oos.writeObject(g);
            }
        } catch (IOException e) {
            throw new GebruikerPersistentieException("GebruikerOfflineMapper");
        }
    }

    @Override
    public void update() {

    }

    @Override
    public Set<Gebruiker> getGebruikers() {
        return gebruikerSet;
    }

    @Override
    public void voegGebruikerToe(Gebruiker g) {
        gebruikerSet.add(g);
        schrijfGebruikers();
    }

    @Override
    public void verwijderGebruiker(Gebruiker g) {
        gebruikerSet.remove(g);
        schrijfGebruikers();
    }

    @Override
    public void updateGebruiker(Gebruiker g) {
        schrijfGebruikers();
    }
}
