package domein;

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
    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public void setSessie(Sessie sessie) {
        this.sessie = sessie;
    }

    public void setGebruiker(Gebruiker gebruiker) {
        this.gebruiker = gebruiker;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }

    public void setType(String type) {
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
