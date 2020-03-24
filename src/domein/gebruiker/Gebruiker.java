package domein.gebruiker;

import com.sun.istack.NotNull;
import domein.Media;
import domein.PasswordUtils;
import domein.interfacesDomein.IGebruiker;
import exceptions.domein.GebruikerException;
import language.Talen;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "gebruiker")
public class Gebruiker implements IGebruiker, Serializable {

    private static final long serialVersionUID = 2236402867161072684L;
    //region Variabelen
    //Primairy key
    @Id
    @NotNull
    private String gebruikersnaam;
    @NotNull
    private String naam;
    @NotNull
    private String wachtwoord;
    private boolean verwijderd = false;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Media profielfoto;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private GebruikerProfielState currentProfiel;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private GebruikerStatusState currentStatus;

    private int inlogPogingen;
    private LocalDate laatstIngelogd;
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
    public Gebruiker(String naam, String gebruikersnaam, String gebruikersprofiel, String gebruikersstatus, String profielfoto, int aantalInlogPogingen, String wachtwoord) {
        setNaam(naam);
        setGebruikersnaam(gebruikersnaam);
        setCurrentProfiel(gebruikersprofiel);
        setCurrentStatus(gebruikersstatus);
        setProfielfoto("storage/profielfotos/profielfoto.png");
        setWachtwoord(wachtwoord);
        this.inlogPogingen = 0;
        this.laatstIngelogd = LocalDate.now();
    }

    /**
     * Constructor voor aanmaken van een gebruiker zonder profielfoto
     *
     * @param naam              (String) ==> Naam van de gebruiker
     * @param gebruikersnaam    (String) ==> Februikersnaam van de gebruiker voor het Chamillo platform
     * @param gebruikersprofiel (String) ==> Profiel dat de gebruiker aanneemt in het algemeen
     * @param gebruikersstatus  (String) ==> Inlogstatus van de gebruiker
     */
    public Gebruiker(String naam, String gebruikersnaam, String gebruikersprofiel, String gebruikersstatus) {
        this(naam, gebruikersnaam, gebruikersprofiel, gebruikersstatus, "storage/profielfotos/profielfoto.png", 0, "testExcluded");
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
    public Gebruiker(String naam, String gebruikersnaam, String gebruikersprofiel, String gebruikersstatus, String profielfoto, String wachtwoord) {
        this(naam, gebruikersnaam, gebruikersprofiel, gebruikersstatus,
                profielfoto, 0, wachtwoord);
    }
    //endregion


    //region Setters
    private void setProfielfoto(String path) {
        //File file = new File("storage/profielfotos/profielfoto.png");
        //byte[] bFile = new byte[(int) file.length()];
        this.profielfoto = new Media(this, "storage/profielfotos/profielfoto.png", "FOTO");
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


    private void setCurrentProfiel(String gebruikersprofiel) {
        if (gebruikersprofiel == null || gebruikersprofiel.isBlank()) {
            throw new GebruikerException("Gebruikersprofiel is leeg of null");
        }
        switch (gebruikersprofiel.toLowerCase()) {
            case "hoofdverantwoordelijke":
                toProfielState(new HoofdverantwoordelijkeState(this));
                break;
            case "verantwoordelijke":
                toProfielState(new VerantwoordelijkeState(this));
                break;
            case "gebruiker":
                toProfielState(new GebruikerState(this));
                break;
            default:
                throw new GebruikerException("Gebruikersprofiel onbekend " + gebruikersprofiel);
        }
    }

    private void toProfielState(GebruikerProfielState profielState) {

        currentProfiel = profielState;
    }


    private void setCurrentStatus(String status) {
        if (status == null || status.isBlank()) {
            throw new GebruikerException("Gebruikersprofiel is leeg of null");
        }
        switch (status.toLowerCase()) {
            case "actief":
                toStatusState(new ActiefStatusState(this));
                break;
            case "geblokkeerd":
                toStatusState(new GeblokkeerdStatusState(this));
                break;
            case "niet actief":
                toStatusState(new NietActiefStatusState(this));
                break;
            default:
                throw new GebruikerException("Gebruikerstatus onbekend " +status);
        }
    }

    private void toStatusState(GebruikerStatusState statusState) {
        this.currentStatus = statusState;
    }

    public void setVerwijderd(boolean verwijderd) {
        this.verwijderd = verwijderd;
    }

    /*
    private void setAantalInlogPogingen(int aantalInlogPogingen) {
        if (aantalInlogPogingen < 0) {
            throw new GebruikerException();
        }
        if (aantalInlogPogingen > 3) {
            setCurrentStatus(Gebruikersstatus.GEBLOKKEERD);
        }
        this.aantalInlogPogingen = aantalInlogPogingen;
    }

    public void addInlogPoging() {
        setAantalInlogPogingen(aantalInlogPogingen + 1);
    }

    public void setIngelogd() {
        this.laatstIngelogd = LocalDateTime.now();
    }

     */

    public void setWachtwoord(String wachtwoord) {
        this.wachtwoord = PasswordUtils.generateSecurePassword(wachtwoord);
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
    public String getStatus() {
        return currentStatus.getStatus();
    }

    @Override
    public String getGebruikersprofiel() {
        return currentProfiel.getProfiel();
    }

    @Override
    public String getProfielfoto() {
        return profielfoto.getLocatie();
    }

    @Override
    public LocalDate getLaatstIngelogd() {
        return laatstIngelogd;
    }

    @Override
    public String getWachtwoord() {
        return wachtwoord;
    }

    public GebruikerStatusState getGebruikerStatusState() {
        return currentStatus;
    }

    public GebruikerProfielState getGebruikerProfielState() {
        return currentProfiel;
    }
    //endregion

    //region methodes

    /**
     * @param gegevens (String naam, String gebruikersnaam, String profiel, String Status)
     */
    public void update(List<String> gegevens) {
        try {
            if (gegevens.get(0) != null && !gegevens.get(0).isBlank()) {
                setNaam(gegevens.get(0));
            }
            if (gegevens.get(1) != null && !gegevens.get(1).isBlank()) {
                setGebruikersnaam(gegevens.get(1));
            }
            if (gegevens.get(3) != null && !gegevens.get(3).isBlank()) {
                setCurrentProfiel(gegevens.get(3));
            }
            if (gegevens.get(2) != null && !gegevens.get(2).isBlank()) {
                setCurrentStatus(gegevens.get(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new GebruikerException("Update");
        }
    }
    //endregion

    //region toString

    @Override
    public String toString() {
        return getNaam();
    }

    public String toString_Compleet() {
        return String.format("%s: %s%n%s: %s%n%s: %s%n%s: %s%n",
                Talen.getString("Gebruiker.naam"), naam, Talen.getString("Gebruiker.gebruikersnaam"), gebruikersnaam, Talen.getString("Gebruiker.type"), getGebruikersprofiel(), Talen.getString("Gebruiker.status"), getStatus());
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
