package persistentie.mappersOffline;

import domein.Gebruiker;
import domein.Gebruikersprofielen;
import domein.Gebruikersstatus;
import exceptions.persistentie.offline.GebruikerOfflineMapperException;
import persistentie.mappersAbs.GebruikerMapperAb;

import java.io.*;
import java.util.Set;

public class GebruikerOfflineMapper extends GebruikerMapperAb {

    private final File gebruikersoffline;
    private boolean initialiseren = false;

    public GebruikerOfflineMapper() {
        super();
        gebruikersoffline = new File("src/offlineData/initData/Gebruiker");
        if(initialiseren){
            maakGebruikers();
        }else {
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
            //throw new GebruikerOfflineMapperException(e.getCause());
            e.printStackTrace();
        }
    }

    @Override
    public void schrijfGebruikers() {
        try {
            FileOutputStream fos = new FileOutputStream("src/offlineData/serieel/Gebruiker.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for (Gebruiker g : gebruikerSet) {
                oos.writeObject(g);
            }
        } catch (IOException e) {
            throw new GebruikerOfflineMapperException();
        }
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
