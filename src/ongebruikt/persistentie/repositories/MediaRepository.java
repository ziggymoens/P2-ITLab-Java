package ongebruikt.persistentie.repositories;

import domein.Media;
import exceptions.persistentie.MediaPersistentieException;
import ongebruikt.persistentie.PersistentieController;
import ongebruikt.persistentie.mappercontrollers.MediaMapperController;

import java.util.List;

public class MediaRepository {
    private MediaMapperController mapper;

    public MediaRepository() {
        this.mapper = new MediaMapperController();
    }

    public void setPersistenieController(PersistentieController pc){
        mapper.setPersistentieController(pc);
    }

    public List<Media> getMedia() {
        return mapper.getMedia();
    }

    public void beheerMedia(String optie, Media media) {
        switch (optie) {
            case "CREATE":
                mapper.voegMediaToe(media);
                break;
            case "READ":
                geefMediaMetId(media.getMediaId());
                break;
            case "UPDATE":
                mapper.updateMedia(media);
                break;
            case "DELETE":
                mapper.verwijderMedia(media);
                break;
            default:
                throw new MediaPersistentieException("MediaRepository");
        }
    }

    private Media geefMediaMetId(String id) {
        return mapper.getMedia().stream().filter(a -> a.getMediaId().equals(id)).findFirst().orElse(null);
    }

    public void schrijfWeg() {
        mapper.schrijfMedia();
    }

    private void update() {
        throw new UnsupportedOperationException();
    }

    public void initData() {
        mapper.initData();
    }
}
