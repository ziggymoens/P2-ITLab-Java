package persistentie.mappers;

import domein.Media;
import persistentie.PersistentieController;

import java.util.ArrayList;
import java.util.List;

public abstract class MediaMapper {
    protected List<Media> mediaList;
    protected PersistentieController persistentieController;

    public MediaMapper() {
        this.mediaList = new ArrayList<>();
    }

    public abstract List<Media> getMedia();

    public abstract void voegMediaToe(Media media);

    public abstract void verwijderMedia(Media media);

    public abstract void updateMedia(Media media);

    public abstract void schrijfMedia();

    public abstract void setPersistentieController(PersistentieController persistentieController);

    public abstract void initData();
}
