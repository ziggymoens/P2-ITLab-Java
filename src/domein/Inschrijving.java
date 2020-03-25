package domein;

import com.sun.istack.NotNull;
import domein.gebruiker.Gebruiker;
import domein.interfacesDomein.IGebruiker;
import domein.interfacesDomein.IInschrijving;
import domein.sessie.Sessie;
import exceptions.domein.InschrijvingException;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "inschrijving")
public class Inschrijving implements IInschrijving {
    //region Variabelen
    //Primairy key
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inschrijvingKey")
    @GenericGenerator(
            name = "inschrijvingKey",
            strategy = "domein.JPAIdGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = JPAIdGenerator.VALUE_PREFIX_PARAMETER, value = "I1920-"),
                    @org.hibernate.annotations.Parameter(name = JPAIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%06d")})
    @NotNull
    private String inschrijvingsId;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Gebruiker gebruiker;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Sessie sessie;
    @NotNull
    private LocalDateTime inschrijvingsdatum;
    @NotNull
    private boolean statusAanwezigheid = false;
    private boolean verwijderd = false;
    //endregion

    //region Constructor

    /**
     * Constructor voor JPA
     */
    protected Inschrijving() {
    }

    /**
     * Default constructor voor Inschrijving
     *
     * @param inschrijvingsdatum (LocalDateTime) ==> Datum van inschrijven
     * @param statusAanwezigheid (boolean) ==> status aanwezigheid van de gebruiker
     */
    public Inschrijving(Sessie sessie, Gebruiker gebruiker, LocalDateTime inschrijvingsdatum, boolean statusAanwezigheid) {
        setSessie(sessie);
        setGebruiker(gebruiker);
        setInschrijvingsdatum(inschrijvingsdatum);
        setStatusAanwezigheid(statusAanwezigheid);
    }

    /**
     * Constructor voor aanmaken inschrijving zonder boolean
     *
     * @param inschrijvingsdatum (LocalDateTime) ==> Datum van inschrijven
     */
    public Inschrijving(Sessie sessie, Gebruiker gebruiker, LocalDateTime inschrijvingsdatum) {
        this(sessie, gebruiker, inschrijvingsdatum, false);
    }

    //endregion

    //region Setters

    public void setSessie(Sessie sessie) {
        if (sessie == null) {
            throw new InschrijvingException();
        }
        this.sessie = sessie;
    }

    private void setInschrijvingsdatum(LocalDateTime inschrijvingsdatum) {
        if (inschrijvingsdatum == null) {
            throw new InschrijvingException();
        }
        this.inschrijvingsdatum = inschrijvingsdatum;
    }

    public void setStatusAanwezigheid(boolean statusAanwezigheid) {
        this.statusAanwezigheid = statusAanwezigheid;
    }

    public void setGebruiker(Gebruiker gebruiker) {
        if (gebruiker == null) {
            throw new InschrijvingException();
        }
        this.gebruiker = gebruiker;
    }

    public void setVerwijderd(boolean verwijderd) {
        this.verwijderd = verwijderd;
    }

    //endregion

    //region Getters
    @Override
    public LocalDateTime getInschrijvingsdatum() {
        return inschrijvingsdatum;
    }

    @Override
    public boolean isStatusAanwezigheid() {
        return statusAanwezigheid;
    }

    @Override
    public String getInschrijvingsId() {
        return inschrijvingsId;
    }

    @Override
    public IGebruiker getIGebruiker() {
        return gebruiker;
    }

    public Gebruiker getGebruiker() {
        return gebruiker;
    }

    public boolean getVerwijderd() {return verwijderd;}
    //endregion

    //region Equals & Hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inschrijving that = (Inschrijving) o;
        return Objects.equals(inschrijvingsId, that.inschrijvingsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inschrijvingsId);
    }
    //endregion

    //region toString

    @Override
    public String toString() {
        return String.format("%s %s %s", gebruiker.getNaam(), inschrijvingsdatum, statusAanwezigheid);
    }

    @Override
    public String toString_Compleet() {
        return String.format("Inschrijving: %s%nIngeschreven gebruiker: %s%nIngeschreven op: %s%n", inschrijvingsId, gebruiker.getNaam(), toString_inschrijvingsDatum());
    }

    @Override
    public String toString_Kort() {
        return String.format("%s - %s", inschrijvingsId, gebruiker.getNaam());
    }

    public String toString_inschrijvingsDatum() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return String.format("%s", inschrijvingsdatum.format(formatter));
    }

    /**
     * @param gegevens (Gebruiker gebruiker, LocalDateTime inschrijvingsdatum, boolean aanwezigheid)
     */
    public void update(List<Object> gegevens) {
        try {
            if (gegevens.get(0) != null) {
                setInschrijvingsdatum((LocalDateTime) gegevens.get(0));
            }
            if (gegevens.get(1) != null) {
                setStatusAanwezigheid((boolean) gegevens.get(1));
            }
        } catch (Exception e) {
            throw new InschrijvingException("Update");
        }
    }

    //endregion
}
