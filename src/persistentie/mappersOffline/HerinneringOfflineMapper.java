package persistentie.mappersOffline;

import domein.Herinnering;
import domein.Gebruiker;
import domein.Sessie;
import exceptions.persistentie.HerinneringPersistentieException;
import persistentie.PersistentieController;
import persistentie.mappers.HerinneringMapper;

import java.io.*;
import java.time.LocalDateTime;
import java.util.List;

public class HerinneringOfflineMapper extends HerinneringMapper {

    private final File herinneringenOffline = new File("src/offlineData/initData/Herinnering");

    public HerinneringOfflineMapper() {
        super();
    }

    @Override
    public void initData() {
        if (Initialiseren.isInitialiseren()) {
            maakHerinneringen();
        } else {
            leesHerinneringen();
        }
    }

    private void maakHerinneringen() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(herinneringenOffline));
            String line;
            while ((line = br.readLine()) != null) {
                String[] her = line.split(";");
                Gebruiker gebruiker = persistentieController.geefGebruikerMetCode(her[2]);
                Sessie sessie = persistentieController.geefSessieMetId(her[3]);
                herinneringList.add(new Herinnering(her[0], Integer.parseInt(her[1]), gebruiker, sessie, LocalDateTime.parse(her[4]), her[5]));
            }
        } catch (IOException e) {
            throw new HerinneringPersistentieException("HerinneringOfflineMapper");
        }
        schrijfHerinneringen();
    }

    private void leesHerinneringen() {
        try {
            FileInputStream fis = new FileInputStream("src/offlineData/serieel/Herinnering.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            while (true) {
                Herinnering herinnering = (Herinnering) ois.readObject();
                herinneringList.add(herinnering);
            }
        } catch (EOFException ignored) {
        } catch (IOException | ClassNotFoundException e) {
            throw new HerinneringPersistentieException("HerinneringOfflineMapper");
        }
    }

    @Override
    public void schrijfHerinneringen() {
        try {
            FileOutputStream fos = new FileOutputStream("src/offlineData/serieel/Herinnering.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for (Herinnering herinnering : herinneringList) {
                oos.writeObject(herinnering);
            }
        } catch (IOException e) {
            throw new HerinneringPersistentieException("HerinneringOfflineMapper");
        }
    }

    @Override
    public void setPersistentieController(PersistentieController persistentieController) {
        this.persistentieController = persistentieController;
    }

    @Override
    public List<Herinnering> getHerinneringen() {
        return herinneringList;
    }

    @Override
    public void voegHerinneringToe(Herinnering herinnering) {
        herinneringList.add(herinnering);
    }

    @Override
    public void verwijderHerinnering(Herinnering herinnering) {
        herinneringList.remove(herinnering);
    }

    @Override
    public void updateHerinnering(Herinnering herinnering) {
        throw new UnsupportedOperationException();
    }
}
