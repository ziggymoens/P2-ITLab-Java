package domein.domeinklassen;

import domein.enums.MediaTypes;
import domein.interfacesDomein.IMedia;
import exceptions.domein.MediaException;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "media")
public class Media implements IMedia {

    //region Variabelen
    //Primairy key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mediaId;

    private String locatie;
    private MediaTypes type;
//endregion

    //region Constructor

    /**
     * Constructor voor JPA
     */
    protected Media() {
    }

    /**
     * Default constructor voor Media
     *
     * @param locatie (String) ==> locatie van het mediaobject in het project
     * @param type    (MediaType) ==> type van media
     */
    public Media(String locatie, MediaTypes type) {
        setLocatie(locatie);
        setType(type);
    }

    /**
     * Constructor voor aanmaken Media met onbekend type
     *
     * @param locatie (String) ==> locatie van het mediaobject in het project
     */
    public Media(String locatie) {
        this(locatie, MediaTypes.ONBEKEND);
    }

    /**
     * Constructor voor aanmaken Media met String type
     *
     * @param locatie (String) ==> locatie van het mediaobject in het project
     * @param type    (String) ==> type van media
     */
    public Media(String locatie, String type) {
        this(locatie, Arrays.stream(MediaTypes.values()).filter(t -> t.toString().equals(type)).findFirst().orElse(MediaTypes.ONBEKEND));
    }
    //endregion

    //region Setters
    private void setLocatie(String locatie) {
        if (locatie == null || locatie.isBlank())
            throw new MediaException();
        this.locatie = locatie;
    }

    private void setType(MediaTypes type) {
        if (type == null) {
            throw new MediaException();
        }
        this.type = type;
    }
    //endregion

    //region Getters

    public int getMediaId() {
        return mediaId;
    }

    public String getLocatie() {
        return locatie;
    }

    public String getTypeString() {
        return type.toString();
    }

    public MediaTypes getType() {
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
}
