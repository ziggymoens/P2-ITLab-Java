package persistentie.offline;

import domein.Gebruiker;
import domein.Gebruikersprofielen;
import domein.Gebruikersstatus;
import exceptions.persistentie.offline.GebruikerOfflineMapperException;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class GebruikerOfflineMapper {

    private Set<Gebruiker> gebruikerSet;
    private final File gebruikersoffline;

    public GebruikerOfflineMapper() {
        this.gebruikerSet = new HashSet<>();
        gebruikersoffline = new File("src/offlineData/initData/Gebruiker");
        //maakGebruikers();
    }

    private void maakGebruikers() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(gebruikersoffline));
            String line;
            while ((line = br.readLine()) != null) {
                String[] geb = line.split(";");
                Gebruikersprofielen profiel = Gebruikersprofielen.valueOf(geb[2].toUpperCase());
                Gebruikersstatus status = Gebruikersstatus.valueOf(geb[3].toUpperCase());
                String profielfoto = null;
                if (geb.length == 5) {
                    profielfoto = geb[4];
                }
                gebruikerSet.add(new Gebruiker(geb[0], geb[1], profiel, status, profielfoto));
            }
        } catch (IOException e) {
            throw new GebruikerOfflineMapperException();
        }
    }

    public void leesGebruikers() {
        try {
            FileInputStream fis = new FileInputStream("src/offlineData/serieel/Gebruiker.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            while (true) {
                Gebruiker gebruiker = (Gebruiker) ois.readObject();
                gebruikerSet.add(gebruiker);
            }
        } catch (EOFException ignored) {
        } catch (IOException | ClassNotFoundException e) {
            throw new GebruikerOfflineMapperException();
        }
    }

    public void schrijfGebruikers(Set<Gebruiker> gebruikers) {
        try {
            FileOutputStream fos = new FileOutputStream("src/offlineData/serieel/Gebruiker.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for (Gebruiker g : gebruikers) {
                oos.writeObject(g);
            }
        } catch (IOException e) {
            throw new GebruikerOfflineMapperException();
        }
    }


    public Set<Gebruiker> getGebruikerSet() {
        return gebruikerSet;
    }
}
