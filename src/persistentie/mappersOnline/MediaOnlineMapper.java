package persistentie.mappersOnline;

import domein.Media;
import persistentie.PersistentieController;
import persistentie.mappers.MediaMapper;

import java.util.List;

public class MediaOnlineMapper extends MediaMapper {

    public MediaOnlineMapper() {
        super();
    }

    @Override
    public void initData() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Media> getMedia() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void voegMediaToe(Media media) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void verwijderMedia(Media media) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateMedia(Media media) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void schrijfMedia() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setPersistentieController(PersistentieController persistentieController) {
        throw new UnsupportedOperationException();
    }
}
