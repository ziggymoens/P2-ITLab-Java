package domein;

import com.sun.istack.NotNull;
import domein.enums.MediaType;
import domein.gebruiker.Gebruiker;
import domein.interfacesDomein.IGebruiker;
import domein.interfacesDomein.IMedia;
import domein.sessie.Sessie;
import exceptions.domein.MediaException;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
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
    @NotNull
    private String mediaId;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Sessie sessie;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Gebruiker gebruiker;
    @NotNull
    private String locatie;
    @NotNull
    @Enumerated(EnumType.STRING)
    private MediaType type;
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
    public Media(Sessie sessie, Gebruiker gebruiker, String locatie, MediaType type) {
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
        this(sessie, gebruiker, locatie, MediaType.ONBEKEND);
    }

    /**
     * Constructor voor aanmaken Media met String type
     *
     * @param locatie (String) ==> locatie van het mediaobject in het project
     * @param type    (String) ==> type van media
     */
    public Media(Sessie sessie, Gebruiker gebruiker, String locatie, String type) {
        this(sessie, gebruiker, locatie, Arrays.stream(MediaType.values()).filter(t -> t.toString().equals(type)).findFirst().orElse(MediaType.ONBEKEND));
    }
    //endregion

    //region Setters

    public void setSessie(Sessie sessie) {
        if (sessie == null) {
            throw new MediaException();
        }
        this.sessie = sessie;
    }

    private void setLocatie(String locatie) {
        if (locatie == null || locatie.isBlank())
            throw new MediaException();
        this.locatie = locatie;
    }

    private void setType(MediaType type) {
        if (type == null)
            throw new MediaException();
        if (Arrays.stream(MediaType.values()).filter(g -> g.toString().equals(type.toString())).findFirst().orElse(null) == null) {
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

    public MediaType getType() {
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

    public void update(List<Object> gegevens) {
        try {
            if (gegevens.get(0) != null) {
                setGebruiker((Gebruiker) gegevens.get(0));
            }
            if (gegevens.get(1) != null && !((String) gegevens.get(1)).isBlank()) {
                setLocatie((String) gegevens.get(1));
            }
            if (gegevens.get(2) != null && !((String) gegevens.get(0)).isBlank()) {
                setType(Arrays.stream(MediaType.values()).filter(t -> t.toString().equals((String) gegevens.get(2))).findFirst().orElse(null));
            }
        } catch (Exception e) {
            throw new MediaException("Update");
        }
    }
    //endregion
}
