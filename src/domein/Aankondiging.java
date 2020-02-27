package domein;

import domein.interfacesDomein.IAankondiging;
import domein.interfacesDomein.IGebruiker;
import domein.interfacesDomein.IHerinnering;
import exceptions.domein.HerinneringException;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "aankondiging")
public class Aankondiging implements IAankondiging {
    //region Variabelen
    //Primairy key
    @Id
    @GenericGenerator(
            name = "aankondigingKey",
            strategy = "domein.JPAIdGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = JPAIdGenerator.VALUE_PREFIX_PARAMETER, value = "A1920-"),
                    @org.hibernate.annotations.Parameter(name = JPAIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%06d")})
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "aankondigingKey")
    //A toevoegen --> generated value
    private String aankondigingsId;

    @OneToOne(cascade = CascadeType.ALL)
    private Herinnering herinnering;
    @ManyToOne(fetch = FetchType.LAZY)
    private Gebruiker gebruiker;

    private LocalDateTime publicatiedatum;
    private String inhoud;
    private boolean automatischeHerinnering;

    private boolean verwijderd = false;
    //mapping

    //private Herinnering herinnering;

    //endregion

    //region Constructor

    /**
     * Constructor voor JPA
     */
    protected Aankondiging() {
    }

    public Aankondiging(Gebruiker gebruiker, LocalDateTime publicatiedatum, String inhoud, boolean automatischeHerinnering, int dagenVooraf) {
        if (automatischeHerinnering) {
            setHerinnering(dagenVooraf);
        }
        setInhoud(inhoud);
        setPublicatiedatum(publicatiedatum);
        setAutomatischeHerinnering(automatischeHerinnering);
        setGebruiker(gebruiker);
    }
    /**
     * @param publicatiedatum ==> De datum dat de aankondiging gemaakt is
     * @param inhoud          ==> inhoud van de aankondiging
     */
    public Aankondiging(Gebruiker gebruiker, LocalDateTime publicatiedatum, String inhoud) {
        this(gebruiker, publicatiedatum, inhoud, false, 0);
    }

    public Aankondiging(Gebruiker gebruiker, LocalDateTime publicatiedatum, String tekst, boolean automatischeHerinnering, Herinnering h) {
        this(gebruiker, publicatiedatum, tekst, automatischeHerinnering, h.getDagenVoorafInt());
        setHerinnering(h.getDagenVoorafInt());
    }
    //endregion

    //region Setters

    public void setGebruiker(Gebruiker gebruiker) {
        if(gebruiker == null){
            throw new HerinneringException();
        }
        this.gebruiker = gebruiker;
    }

    private void setPublicatiedatum(LocalDateTime publicatiedatum) {
        if (publicatiedatum == null) {
            throw new HerinneringException();
        }
        this.publicatiedatum = publicatiedatum;
    }

    private void setInhoud(String inhoud) {
        if (inhoud == null || inhoud.isBlank()) {
            throw new HerinneringException();
        }
        this.inhoud = inhoud;
    }

    public void setHerinnering(int dagenVooraf) {
        if(dagenVooraf < 0)
            throw new HerinneringException();
        Herinnering herinnering = new Herinnering(dagenVooraf);
        if (automatischeHerinnering && herinnering == null) {
            throw new HerinneringException();
        }
        this.herinnering = herinnering;
    }

    public void setAutomatischeHerinnering(boolean automatischeHerinnering) {
        this.automatischeHerinnering = automatischeHerinnering;
    }

    public void setVerwijderd(boolean verwijderd) {
        this.verwijderd = verwijderd;
    }

    public void setHerinnering(Herinnering herinnering) {
        this.herinnering = herinnering;
        automatischeHerinnering = true;
    }
    //endregion

    //region Getters
    @Override
    public LocalDateTime getPublicatiedatum() {
        return publicatiedatum;
    }

    @Override
    public Gebruiker getPublicist() {
        return gebruiker;
    }

    @Override
    public String getInhoud() {
        return inhoud;
    }

    @Override
    public String getAankondigingsId() {
        return aankondigingsId;
    }

    @Override
    public IHerinnering getIHerinnering() {
        return herinnering;
    }

    @Override
    public boolean isAutomatischeHerinnering() {
        return automatischeHerinnering;
    }

    @Override
    public IGebruiker getIGebruiker(){ return gebruiker;}

    public Gebruiker getGebruiker() {
        return gebruiker;
    }

    public Herinnering getHerinnering() {
        return herinnering;
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
        return String.format("%s - %s", aankondigingsId, gebruiker.getNaam());
    }
    @Override
    public String toString_Compleet() {
        return String.format("Aankondiging: %s%nGeplaatst door: %s%nGeplaats op: %s%nAutomatische herinnering? %s%nInhoud: %s%n", aankondigingsId, gebruiker.getNaam(), publicatiedatum.toString(), automatischeHerinnering? String.format("ja%nAantal dagen voor de sessie: %d%n", herinnering.getDagenVoorafInt()):"nee", inhoud);
    }
    //endregion
}
