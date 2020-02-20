package ongebruikt.persistentie.mappers.mappersOffline;

import domein.Lokaal;
import exceptions.persistentie.LokaalPersistentieException;
import ongebruikt.persistentie.mappers.LokaalMapper;

import java.io.*;
import java.util.Set;

public class LokaalOfflineMapper extends LokaalMapper {
    private final File lokalenOffline = new File("src/ongebruikt.offlineData/initData/Lokalen");

    public LokaalOfflineMapper() {
        super();
    }

    @Override
    public void initData() {
        if(Initialiseren.isInitialiseren()){
            maakLokalen();
        }else{
            leesLokalen();
        }
    }

    private void maakLokalen() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(lokalenOffline));
            String line;
            while ((line = br.readLine()) != null) {
                String[] lokaal = line.split(";");
                lokaalSet.add(new Lokaal(lokaal[0], Integer.parseInt(lokaal[1])));
            }
        } catch (IOException e) {
            throw new LokaalPersistentieException("LokalenOfflineMapper");
        }
        schrijfLokalen();
    }

    public void leesLokalen() {
        try {
            FileInputStream fis = new FileInputStream("src/ongebruikt.offlineData/serieel/Lokalen.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            while (true) {
                Lokaal lokaal = (Lokaal) ois.readObject();
                lokaalSet.add(lokaal);
            }
        } catch (EOFException ignored) {
        } catch (IOException | ClassNotFoundException e) {
            throw new LokaalPersistentieException("LokalenOfflineMapper");
        }
    }

    @Override
    public void schrijfLokalen() {
        try {
            FileOutputStream fos = new FileOutputStream("src/ongebruikt.offlineData/serieel/Lokalen.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for (Lokaal l : lokaalSet) {
                oos.writeObject(l);
            }
        } catch (IOException e) {
            throw new LokaalPersistentieException("LokalenOfflineMapper");
        }
    }

    @Override
    public Set<Lokaal> getLokalen() {
        return lokaalSet;
    }

    @Override
    public void voegLokaalToe(Lokaal l) {
        lokaalSet.add(l);
    }

    @Override
    public void verwijderLokaal(Lokaal l) {
        lokaalSet.remove(l);
    }

    @Override
    public void updateLokaal(Lokaal l) {
        throw new UnsupportedOperationException();
    }
}