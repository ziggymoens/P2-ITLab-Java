package domein;

import domein.interfacesDomein.IGebruiker;
import domein.interfacesDomein.IInschrijving;
import exceptions.domein.InschrijvingException;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
@Table(name = "inschijving")
public class Inschrijving implements IInschrijving {
    //region Variabelen
    //Primairy key
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inschrijvingKey")
    @GenericGenerator(
            name = "inschrijvingKey",
            strategy = "domein.JPAIdGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = JPAIdGenerator.VALUE_PREFIX_PARAMETER, value = "I20-"),
                    @org.hibernate.annotations.Parameter(name = JPAIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%06d")})
    private String inschrijvingsId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Gebruiker gebruiker;

    private LocalDateTime inschrijvingsdatum;
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
    public Inschrijving(Gebruiker gebruiker, LocalDateTime inschrijvingsdatum, boolean statusAanwezigheid) {
        setGebruiker(gebruiker);
        setInschrijvingsdatum(inschrijvingsdatum);
        setStatusAanwezigheid(statusAanwezigheid);
    }

    /**
     * Constructor voor aanmaken inschrijving zonder boolean
     *
     * @param inschrijvingsdatum (LocalDateTime) ==> Datum van inschrijven
     */
    public Inschrijving(Gebruiker gebruiker, LocalDateTime inschrijvingsdatum) {
        this(gebruiker, inschrijvingsdatum, false);
    }

    //endregion

    //region Setters
    private void setInschrijvingsdatum(LocalDateTime inschrijvingsdatum) {
        if (inschrijvingsdatum == null) {
            throw new InschrijvingException();
        }
        this.inschrijvingsdatum = inschrijvingsdatum;
    }

    private void setStatusAanwezigheid(boolean statusAanwezigheid) {
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
        return String.format("%s - %s", inschrijvingsId, gebruiker.getNaam());
    }
    @Override
    public String toString_Compleet() {
        return String.format("Inschrijving: %s%nIngeschreven gebruiker: %s%nIngeschreven op: %s%n", inschrijvingsId, gebruiker.getNaam(), toString_inschrijvingsDatum());
    }
    public String toString_inschrijvingsDatum(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return String.format("%s", inschrijvingsdatum.format(formatter));
    }

    //endregion
}
