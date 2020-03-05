package domein;

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
            strategy = "domein.JPAIdGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = JPAIdGenerator.VALUE_PREFIX_PARAMETER, value = "M1920-"),
                    @org.hibernate.annotations.Parameter(name = JPAIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%06d")})
    private String mediaId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Sessie sessie;

    @ManyToOne(fetch = FetchType.LAZY)
    private Gebruiker gebruiker;

    private String locatie;
    private MediaTypes type;
    private boolean verwijderd = false;
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
    public Media(Sessie sessie, Gebruiker gebruiker, String locatie, MediaTypes type) {
        setSessie(sessie);
        setGebruiker(gebruiker);
        setLocatie(locatie);
        setType(type);
    }

    /**
     * Constructor voor aanmaken Media met onbekend type
     *
     * @param locatie (String) ==> locatie van het mediaobject in het project
     */
    public Media(Sessie sessie, Gebruiker gebruiker, String locatie) {
        this(sessie,gebruiker, locatie, MediaTypes.ONBEKEND);
    }

    /**
     * Constructor voor aanmaken Media met String type
     *
     * @param locatie (String) ==> locatie van het mediaobject in het project
     * @param type    (String) ==> type van media
     */
    public Media(Sessie sessie,Gebruiker gebruiker, String locatie, String type) {
        this(sessie, gebruiker, locatie, Arrays.stream(MediaTypes.values()).filter(t -> t.toString().equals(type)).findFirst().orElse(MediaTypes.ONBEKEND));
    }
    //endregion

    //region Setters

    public void setSessie(Sessie sessie) {
        if(sessie==null){
            throw new MediaException();
        }
        this.sessie = sessie;
    }

    private void setLocatie(String locatie) {
        if (locatie == null || locatie.isBlank())
            throw new MediaException();
        this.locatie = locatie;
    }

    private void setType(MediaTypes type) {
        if (type == null)
            throw new MediaException();
        if(Arrays.stream(MediaTypes.values()).filter(g -> g.toString().equals(type.toString())).findFirst().orElse(null) == null){
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

    public void setVerwijderd(boolean verwijderd) {
        this.verwijderd = verwijderd;
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
        return String.format("%s - %s", mediaId, gebruiker.getNaam());
    }
    @Override
    public String toString_Compleet() {
        return String.format("Media: %s%nGeupload door: %s%nMediatype: %s%n", mediaId, gebruiker.getNaam(), type);
    }
    //endregion
}
