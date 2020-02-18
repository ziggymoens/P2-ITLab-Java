package persistentie.mappersOffline;

import domein.Lokaal;
import exceptions.persistentie.offline.LokalenOfflineMapperException;
import persistentie.mappersAbs.LokaalMapperAb;

import java.io.*;
import java.util.Set;

public class LokalenOfflineMapper extends LokaalMapperAb {
    private final File lokalenOffline;

    public LokalenOfflineMapper() {
        lokalenOffline = new File("src/offlineData/initData/Lokalen");
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
            throw new LokalenOfflineMapperException();
        }
        schrijfLokalen();
    }

    public void leesLokalen() {
        try {
            FileInputStream fis = new FileInputStream("src/offlineData/serieel/Lokalen.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            while (true) {
                Lokaal lokaal = (Lokaal) ois.readObject();
                lokaalSet.add(lokaal);
            }
        } catch (EOFException ignored) {
        } catch (IOException | ClassNotFoundException e) {
            throw new LokalenOfflineMapperException();
        }
    }

    public void schrijfLokalen() {
        try {
            FileOutputStream fos = new FileOutputStream("src/offlineData/serieel/Lokalen.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for (Lokaal l : lokaalSet) {
                oos.writeObject(l);
            }
        } catch (IOException e) {
            throw new LokalenOfflineMapperException();
        }
    }


    public Set<Lokaal> getLokalenSet() {
        return lokaalSet;
    }
}
