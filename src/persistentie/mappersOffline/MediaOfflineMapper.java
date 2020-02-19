package persistentie.mappersOffline;

import domein.Media;
import domein.Gebruiker;
import domein.Sessie;
import exceptions.persistentie.offline.GebruikerOfflineMapperException;
import persistentie.PersistentieController;
import persistentie.mappers.MediaMapper;

import java.io.*;
import java.time.LocalDateTime;
import java.util.List;

public class MediaOfflineMapper extends MediaMapper {

    private final File mediaenOffline = new File("src/offlineData/initData/Media");

    public MediaOfflineMapper() {
        super();
    }

    @Override
    public void initData() {
        if (Initialiseren.isInitialiseren()) {
            maakMediaen();
        } else {
            leesMediaen();
        }
    }

    private void maakMediaen() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(mediaenOffline));
            String line;
            while ((line = br.readLine()) != null) {
                String[] media = line.split(";");
                Gebruiker gebruiker = persistentieController.geefGebruikerMetCode(media[2]);
                Sessie sessie = persistentieController.geefSessieMetId(media[1]);
                mediaList.add(new Media(media[0], sessie, gebruiker, media[3], media[4]));
            }
        } catch (IOException e) {
            throw new GebruikerOfflineMapperException();
        }
        schrijfMedia();
    }

    private void leesMediaen() {
        try {
            FileInputStream fis = new FileInputStream("src/offlineData/serieel/Media.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            while (true) {
                Media media = (Media) ois.readObject();
                mediaList.add(media);
            }
        } catch (EOFException ignored) {
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void schrijfMedia() {
        try {
            FileOutputStream fos = new FileOutputStream("src/offlineData/serieel/Media.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for (Media media : mediaList) {
                oos.writeObject(media);
            }
        } catch (IOException e) {
            throw new GebruikerOfflineMapperException();
        }
    }

    @Override
    public void setPersistentieController(PersistentieController persistentieController) {
        this.persistentieController = persistentieController;
    }

    @Override
    public List<Media> getMedia() {
        return mediaList;
    }

    @Override
    public void voegMediaToe(Media media) {
        mediaList.add(media);
    }

    @Override
    public void verwijderMedia(Media media) {
        mediaList.remove(media);
    }

    @Override
    public void updateMedia(Media media) {
        throw new UnsupportedOperationException();
    }
}
