package domein;

import domein.interfacesDomein.IMedia;
import exceptions.domein.MediaException;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
@Entity
//@Table(name = "media")
public class Media implements IMedia {

    //region Variabelen
    //Primairy key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mediaId;

    private String locatie;
    private String type;
//endregion

    //region Constructor
    protected  Media(){}

    public Media(String locatie, String type) {
        setLocatie(locatie);
        setType(type);
    }
    //endregion

    //region Setters
    /*private void setMediaId(String mediaId) {
        if(mediaId == null || mediaId.isBlank())
            throw new MediaException();
        this.mediaId = mediaId;
    }*/

    private void setLocatie(String locatie) {
        if(locatie == null || locatie.isBlank())
            throw new MediaException();
        this.locatie = locatie;
    }

    private void setType(String type) {
        if(type == null || type.isBlank())
            throw new MediaException();
        this.type = type;
    }
    //endregion

    //region Getters

    public int getMediaId() {
        return mediaId;
    }

    public Sessie getSessie() {
        throw new UnsupportedOperationException();
    }

    public Gebruiker getGebruiker() {
        throw new UnsupportedOperationException();
    }

    public String getLocatie() {
        return locatie;
    }

    public String getType() {
        return type;
    }

    //endregion

    //region Equals & hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Media media = (Media) o;
        return Objects.equals(mediaId, media.mediaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mediaId);
    }
    //endregion

    //region toString
    @Override
    public String toString() {
        return "Media{" +
                "mediaId='" + mediaId + '\'' +
                ", locatie='" + locatie + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
    //endregion
    @ManyToOne(optional = false)
    private Sessie media;

    public Sessie getMedia() {
        return media;
    }

    public void setMedia(Sessie media) {
        this.media = media;
    }
}
