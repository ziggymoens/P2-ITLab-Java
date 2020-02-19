package persistentie.mappercontrollers;

import domein.Media;
import persistentie.Connection;
import persistentie.PersistentieController;
import persistentie.mappers.MediaMapper;
import persistentie.mappers.mappersOffline.MediaOfflineMapper;
import persistentie.mappers.mappersOnline.MediaOnlineMapper;

import java.util.List;

public class MediaMapperController {
    private MediaMapper mapper;

    public MediaMapperController() {
        if(Connection.isONLINE()){
            mapper = new MediaOnlineMapper();
        } else{
            mapper = new MediaOfflineMapper();
        }
    }

    public void setPersistentieController(PersistentieController persistentieController){
        mapper.setPersistentieController(persistentieController);
    }

    public List<Media> getMedia() {
        return mapper.getMedia();
    }

    public void voegMediaToe(Media media) {
        mapper.voegMediaToe(media);
    }

    public void updateMedia(Media media) {
        throw new UnsupportedOperationException();
    }

    public void verwijderMedia(Media media) {
        mapper.verwijderMedia(media);
    }

    public void schrijfMedia() {
        mapper.schrijfMedia();
    }

    public void initData() {
        mapper.initData();
    }
}
