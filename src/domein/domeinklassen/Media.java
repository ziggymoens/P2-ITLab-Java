package domein.domeinklassen;

import domein.enums.MediaTypes;
import domein.interfacesDomein.IGebruiker;
import domein.interfacesDomein.IMedia;
import exceptions.domein.MediaException;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "media")
public class Media implements IMedia {

    //region Variabelen
    //Primairy key
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mediaKey")
    @GenericGenerator(
            name = "mediaKey",
            strategy = "domein.domeinklassen.JPAIdGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = JPAIdGenerator.INCREMENT_PARAM, value = "1"),
                    @org.hibernate.annotations.Parameter(name = JPAIdGenerator.VALUE_PREFIX_PARAMETER, value = "M20-"),
                    @org.hibernate.annotations.Parameter(name = JPAIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%06d")})
    private String mediaId;

    @ManyToOne
    private Gebruiker gebruiker;
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
    public Media(Gebruiker gebruiker, String locatie, MediaTypes type) {
        setGebruiker(gebruiker);
        setLocatie(locatie);
        setType(type);
    }

    /**
     * Constructor voor aanmaken Media met onbekend type
     *
     * @param locatie (String) ==> locatie van het mediaobject in het project
     */
    public Media(Gebruiker gebruiker, String locatie) {
        this(gebruiker, locatie, MediaTypes.ONBEKEND);
    }

    /**
     * Constructor voor aanmaken Media met String type
     *
     * @param locatie (String) ==> locatie van het mediaobject in het project
     * @param type    (String) ==> type van media
     */
    public Media(Gebruiker gebruiker, String locatie, String type) {
        this(gebruiker, locatie, Arrays.stream(MediaTypes.values()).filter(t -> t.toString().equals(type)).findFirst().orElse(MediaTypes.ONBEKEND));
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

    public void setGebruiker(Gebruiker gebruiker) {
        if (gebruiker == null) {
            throw new MediaException();
        }
        this.gebruiker = gebruiker;
    }
    //endregion

    //region Getters

    public String getMediaId() {
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

    @Override
    public IGebruiker getIGebruiker() {
        return gebruiker;
    }

    public Gebruiker getGebruiker() {
        return gebruiker;
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