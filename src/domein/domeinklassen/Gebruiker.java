package domein.domeinklassen;

import domein.enums.Gebruikersprofielen;
import domein.enums.Gebruikersstatus;
import domein.interfacesDomein.*;
import exceptions.domein.GebruikerException;
import language.Talen;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "gebruiker")
public class Gebruiker implements IGebruiker {

    //region Variabelen
    //Primairy key
    @Id
    private String gebruikersnaam;

    private String naam;
    private Gebruikersprofielen gebruikersprofiel;
    private Gebruikersstatus status;

    private String profielfoto;
    //region Constructor

    /**
     * Constructor voor JPA
     */
    protected Gebruiker() {
    }

    /**
     * Default constructor voor gebruiker
     *
     * @param naam              (String) ==> Naam van de gebruiker
     * @param gebruikersnaam    (String) ==> Februikersnaam van de gebruiker voor het Chamillo platform
     * @param gebruikersprofiel (Gebruikersprofiel) ==> Profiel dat de gebruiker aanneemt in het algemeen
     * @param gebruikersstatus  (Gebruikersprofiel) ==> Inlogstatus van de gebruiker
     * @param profielfoto       (Media) ==> profielfoto van de gebruiker
     */
    public Gebruiker(String naam, String gebruikersnaam, Gebruikersprofielen gebruikersprofiel, Gebruikersstatus gebruikersstatus, String profielfoto) {
        setNaam(naam);
        setGebruikersnaam(gebruikersnaam);
        setGebruikersprofielen(gebruikersprofiel);
        setStatus(gebruikersstatus);
        setProfielfoto(profielfoto);
    }

    /**
     * Constructor voor aanmaken van een gebruiker zonder profielfoto
     *
     * @param naam              (String) ==> Naam van de gebruiker
     * @param gebruikersnaam    (String) ==> Februikersnaam van de gebruiker voor het Chamillo platform
     * @param gebruikersprofiel (Gebruikersprofiel) ==> Profiel dat de gebruiker aanneemt in het algemeen
     * @param gebruikersstatus  (Gebruikersprofiel) ==> Inlogstatus van de gebruiker
     */
    public Gebruiker(String naam, String gebruikersnaam, Gebruikersprofielen gebruikersprofiel, Gebruikersstatus gebruikersstatus) {
        this(naam, gebruikersnaam, gebruikersprofiel, gebruikersstatus, null);
    }

    /**
     * Constructor voor aanmaken gebruiker adhv Strings zonder profielfoto
     *
     * @param naam              (String) ==> Naam van de gebruiker
     * @param gebruikersnaam    (String) ==> Februikersnaam van de gebruiker voor het Chamillo platform
     * @param gebruikersprofiel (String) ==> Profiel dat de gebruiker aanneemt in het algemeen
     * @param gebruikersstatus  (String) ==> Inlogstatus van de gebruiker
     */
    public Gebruiker(String naam, String gebruikersnaam, String gebruikersprofiel, String gebruikersstatus) {
        this(naam, gebruikersnaam,
                Arrays.stream(Gebruikersprofielen.values()).filter(g -> g.toString().equals(gebruikersprofiel)).findFirst().orElse(null),
                Arrays.stream(Gebruikersstatus.values()).filter(g -> g.toString().equals(gebruikersstatus)).findFirst().orElse(null),
                null);
    }

    /**
     * Constructor voor aanmaken van gebruiker adhv Strings
     *
     * @param naam              (String) ==> Naam van de gebruiker
     * @param gebruikersnaam    (String) ==> Februikersnaam van de gebruiker voor het Chamillo platform
     * @param gebruikersprofiel (String) ==> Profiel dat de gebruiker aanneemt in het algemeen
     * @param gebruikersstatus  (String) ==> Inlogstatus van de gebruiker
     * @param profielfoto       (String) ==> profielfoto van de gebruiker
     */
    public Gebruiker(String naam, String gebruikersnaam, String gebruikersprofiel, String gebruikersstatus, String profielfoto) {
        this(naam, gebruikersnaam,
                Arrays.stream(Gebruikersprofielen.values()).filter(g -> g.toString().equals(gebruikersprofiel)).findFirst().orElse(null),
                Arrays.stream(Gebruikersstatus.values()).filter(g -> g.toString().equals(gebruikersstatus)).findFirst().orElse(null),
                profielfoto);
    }
    //endregion

    //region Setters
    private void setProfielfoto(String profielfoto) {
        this.profielfoto = profielfoto;
    }

    private void setNaam(String naam) {
        if (naam == null || naam.isBlank()) {
            throw new GebruikerException();
        }
        this.naam = naam;
    }

    private void setGebruikersnaam(String gebruikersnaam) {
        if (gebruikersnaam == null || gebruikersnaam.isBlank()) {
            throw new GebruikerException();
        }
        this.gebruikersnaam = gebruikersnaam;
    }

    private void setGebruikersprofielen(Gebruikersprofielen gebruikersprofielen) {
        if (gebruikersprofielen == null)
            throw new GebruikerException();
        if (Arrays.stream(Gebruikersprofielen.values()).filter(e -> e == gebruikersprofielen).findFirst().orElse(null) == null) {
            throw new GebruikerException("GebruikerException.verantwoordelijkeFoutType");
        }
        this.gebruikersprofiel = gebruikersprofielen;
    }

    private void setStatus(Gebruikersstatus status) {
        if (status == null)
            throw new GebruikerException();
        if (Arrays.stream(Gebruikersstatus.values()).filter(e -> e == status).findFirst().orElse(null) == null) {
            throw new GebruikerException("GebruikerException.verantwoordelijkeFoutStatus");
        }
        this.status = status;
    }
    //endregion

    //region Getters
    @Override
    public String getNaam() {
        return naam;
    }

    @Override
    public String getGebruikersnaam() {
        return gebruikersnaam;
    }

    @Override
    public Gebruikersstatus getStatus() {
        return status;
    }

    @Override
    public Gebruikersprofielen getGebruikersprofiel() {
        return gebruikersprofiel;
    }

    @Override
    public String getProfielfoto() {
        return profielfoto;
    }
    //endregion

    //region toString
    @Override
    public String toString() {
        return String.format("%s: %s%n%s: %s%n%s: %s%n%s: %s%n",
                Talen.getString("Gebruiker.naam"), naam, Talen.getString("Gebruiker.gebruikersnaam"), gebruikersnaam, Talen.getString("Gebruiker.type"), gebruikersprofiel, Talen.getString("Gebruiker.status"), status);
    }
    //endregion

    //region Equals & Hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gebruiker gebruiker = (Gebruiker) o;
        return Objects.equals(gebruikersnaam, gebruiker.gebruikersnaam);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gebruikersnaam);
    }
    //endregion
}
