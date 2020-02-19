package persistentie.mappers.mappersOffline;

import domein.Gebruiker;
import domein.Lokaal;
import domein.Sessie;
import exceptions.persistentie.SessiePersistentieException;
import persistentie.PersistentieController;
import persistentie.mappers.SessieMapper;

import java.io.*;
import java.time.LocalDateTime;
import java.util.List;

public class SessieOfflineMapper extends SessieMapper {
    private final File gebruikersoffline = new File("src/offlineData/initData/Sessies");

    public SessieOfflineMapper() {
        super();
    }

    @Override
    public void initData() {
        if (Initialiseren.isInitialiseren()){
            maakSessies();
        }else{
            leesSessies();
        }
    }

    public void setPersistentieController(PersistentieController pc) {
        persistentieController = pc;
    }

    public void maakSessies() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(gebruikersoffline));
            String line;
            while ((line = br.readLine()) != null) {
                String[] sessie = line.split(";");
                LocalDateTime startSessie = LocalDateTime.parse(sessie[2]);
                LocalDateTime eindeSessie = LocalDateTime.parse(sessie[3]);
                Lokaal lokaal = persistentieController.geefLokaalMetCode(sessie[4]);
                Gebruiker gebruiker = persistentieController.geefGebruikerMetCode(sessie[5]);
                sessieList.add(new Sessie(sessie[0], sessie[1], startSessie, eindeSessie, lokaal, gebruiker));
            }
        } catch (IOException e) {
            throw new SessiePersistentieException("SessieOfflineMapper");
        }
        schrijfSessies();
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
            throw new SessiePersistentieException("SessieOfflineMapper");
        }
    }

    @Override
    public void schrijfSessies() {
        try {
            FileOutputStream fos = new FileOutputStream("src/offlineData/serieel/Sessie.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for (Sessie s : sessieList) {
                oos.writeObject(s);
            }
        } catch (IOException e) {
            throw new SessiePersistentieException("SessieOfflineMapper");
        }
    }

    @Override
    public List<Sessie> getSessies() {
        return sessieList;
    }

    @Override
    public void voegSessieToe(Sessie s) {
        sessieList.add(s);
        schrijfSessies();
    }

    @Override
    public void verwijderSessie(Sessie s) {
        sessieList.remove(s);
        schrijfSessies();
    }

    @Override
    public void updateSessie(Sessie s) {
        throw new UnsupportedOperationException();
    }
}
