package domein;

import exceptions.domein.MediaException;

import java.io.Serializable;
import java.util.Objects;

public class Media implements Serializable {
    private static final long serialVersionUID = 6488538626159580748L;

    //region Variabelen
    //Primairy key
    private String mediaId;

    private Sessie sessie;
    private Gebruiker gebruiker;
    private String locatie;
    private String type;
//endregion

    //region Constructor
    public Media(String mediaId, Sessie sessie, Gebruiker gebruiker, String locatie, String type) {
        setMediaId(mediaId);
        setSessie(sessie);
        setGebruiker(gebruiker);
        setLocatie(locatie);
        setType(type);
    }
    //endregion

    //region Setters
    private void setMediaId(String mediaId) {
        if(mediaId == null || mediaId.isBlank())
            throw new MediaException();
        this.mediaId = mediaId;
    }

    private void setSessie(Sessie sessie) {
        if(sessie == null)
            throw new MediaException();
        this.sessie = sessie;
    }

    private void setGebruiker(Gebruiker gebruiker) {
        if(gebruiker == null)
            throw new MediaException();
        this.gebruiker = gebruiker;
    }

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

    public String getMediaId() {
        return mediaId;
    }

    public Sessie getSessie() {
        return sessie;
    }

    public Gebruiker getGebruiker() {
        return gebruiker;
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
                ", sessie=" + sessie.getSessieId() +
                ", gebruiker=" + gebruiker.getNaam() +
                ", locatie='" + locatie + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
    //endregion
}
