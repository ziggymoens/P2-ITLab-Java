package persistentie.offline;

import domein.Lokaal;
import exceptions.persistentie.offline.LokalenOfflineMapperException;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class LokalenOfflineMapper {

    private Set<Lokaal> lokalenSet;
    private final File lokalenOffline;

    public LokalenOfflineMapper() {
        this.lokalenSet = new HashSet<>();
        lokalenOffline = new File("src/offlineData/initData/Lokalen");
        maakLokalen();
    }

    private void maakLokalen() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(lokalenOffline));
            String line;
            while ((line = br.readLine()) != null) {
                String[] lokaal = line.split(";");
                lokalenSet.add(new Lokaal(lokaal[0], Integer.parseInt(lokaal[1])));
            }
        } catch (IOException e) {
            throw new LokalenOfflineMapperException();
        }
    }

    public void leesLokalen() {
        try {
            FileInputStream fis = new FileInputStream("src/offlineData/serieel/Lokalen.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            while (true) {
                Lokaal lokaal = (Lokaal) ois.readObject();
                lokalenSet.add(lokaal);
            }
        } catch (EOFException ignored) {
        } catch (IOException | ClassNotFoundException e) {
            throw new LokalenOfflineMapperException();
        }
    }

    public void schrijfLokalen(Set<Lokaal> lokalen) {
        try {
            FileOutputStream fos = new FileOutputStream("src/offlineData/serieel/Lokalen.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for (Lokaal l : lokalen) {
                oos.writeObject(l);
            }
        } catch (IOException e) {
            throw new LokalenOfflineMapperException();
        }
    }


    public Set<Lokaal> getLokalenSet() {
        return lokalenSet;
    }
}
