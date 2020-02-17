package persistentie.offline;

import domein.Gebruiker;
import domein.Lokaal;
import domein.Sessie;
import exceptions.persistentie.offline.GebruikerOfflineMapperException;
import exceptions.persistentie.offline.SessieOfflineMapperException;
import persistentie.PersistentieController;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SessieOfflineMapper {
    private List<Sessie> sessieList;
    private final File gebruikersoffline;
    PersistentieController pc;

    public SessieOfflineMapper() {
        this.sessieList = new ArrayList<>();
        gebruikersoffline = new File("src/offlineData/initData/Sessies");
    }

    public void setPersistentieController(PersistentieController pc) {
        this.pc = pc;
    }

    public void maakSessies() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(gebruikersoffline));
            String line;
            while ((line = br.readLine()) != null) {
                String[] sessie = line.split(";");
                LocalDateTime startSessie = LocalDateTime.parse(sessie[1]);
                LocalDateTime eindeSessie = LocalDateTime.parse(sessie[2]);
                Lokaal lokaal = pc.geefLokaalMetCode(sessie[3]);
                int max = lokaal.getAantalPlaatsen();
                Gebruiker gebruiker = pc.geefGebruikerMetCode(sessie[4]);
                sessieList.add(new Sessie(sessie[0], startSessie, eindeSessie, max, lokaal, gebruiker));
            }
        } catch (IOException e) {
            throw new GebruikerOfflineMapperException();
        }
    }

    public void leesSessies() {
        try {
            FileInputStream fis = new FileInputStream("src/offlineData/serieel/Sessie.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            while (true) {
                Sessie sessie = (Sessie) ois.readObject();
                sessieList.add(sessie);
            }
        } catch (EOFException ignored) {
        } catch (IOException | ClassNotFoundException e) {
            throw new SessieOfflineMapperException();
        }
    }

    public void schrijfSessies(List<Sessie> gebruikers) {
        try {
            FileOutputStream fos = new FileOutputStream("src/offlineData/serieel/Sessie.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for (Sessie s : sessieList) {
                oos.writeObject(s);
            }
        } catch (IOException e) {
            throw new GebruikerOfflineMapperException();
        }
    }

    public List<Sessie> getSessieList() {
        return sessieList;
    }
}
