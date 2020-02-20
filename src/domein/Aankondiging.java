package domein;

import domein.interfacesDomein.IAankondiging;
import exceptions.domein.AankondigingException;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "aankondiging")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Aankondiging implements IAankondiging {
    //region Variabelen
    //Primairy key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //A toevoegen --> generated value
    private String aankondigingsId;

    private LocalDateTime publicatiedatum;
    private String inhoud;
    private boolean automatischeHerinnering;
    //mapping
    private Herinnering herinnering;
    //endregion

    //region Constructor
    protected Aankondiging() {
    }

    public Aankondiging(LocalDateTime publicatiedatum, String inhoud) {
        setPublicatiedatum(publicatiedatum);
        setInhoud(inhoud);
    }
    //endregion

    //region Setters
    private void setPublicatiedatum(LocalDateTime publicatiedatum) {
        if (publicatiedatum == null) {
            throw new AankondigingException();
        }
        this.publicatiedatum = publicatiedatum;
    }

    private void setInhoud(String inhoud) {
        if (inhoud == null || inhoud.isBlank()) {
            throw new AankondigingException();
        }
        this.inhoud = inhoud;
    }

    protected void setAankondigingsId(String aankondigingsId) {
        if (aankondigingsId == null || aankondigingsId.isBlank())
            throw new AankondigingException();
        this.aankondigingsId = aankondigingsId;
    }

    //endregion

    //region Getters
    public LocalDateTime getPublicatiedatum() {
        return publicatiedatum;
    }

    public Gebruiker getPublicist() {
        throw new UnsupportedOperationException();
    }

    public String getInhoud() {
        return inhoud;
    }

    public String getAankondigingsId() {
        return aankondigingsId;
    }

    public Sessie getSessie() {
        throw new UnsupportedOperationException();
    }

//endregion

    //region Equals & Hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aankondiging that = (Aankondiging) o;
        return Objects.equals(aankondigingsId, that.aankondigingsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aankondigingsId);
    }
    //endregion

    //region toString

    @Override
    public String toString() {
        return "Aankondiging{" +
                "aankondigingsId='" + aankondigingsId + '\'' +
                ", publicatiedatum=" + publicatiedatum +
                ", inhoud='" + inhoud + '\'' +
                '}';
    }
    //endregion
}
