package domein.gebruiker;

import domein.enums.Gebruikersprofiel;
import domein.enums.Gebruikersstatus;
import domein.interfacesDomein.IGebruiker;
import exceptions.domein.GebruikerException;
import language.Talen;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.File;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "gebruiker")
public class Gebruiker implements IGebruiker {

    //region Variabelen
    //Primairy key
    @Id
    private String gebruikersnaam;

    private String naam;
    private String wachtwoord;
    private byte[] profielfoto;
    private int aantalInlogPogingen;
    private LocalDateTime laatstIngelogd = LocalDateTime.now().minusDays(4);
    private boolean verwijderd = false;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private GebruikerProfielState currentProfiel;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private GebruikerStatusState currentStatus;
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
    public Gebruiker(String naam, String gebruikersnaam, Gebruikersprofiel gebruikersprofiel, Gebruikersstatus gebruikersstatus, String profielfoto, int aantalInlogPogingen, String wachtwoord) {
        setNaam(naam);
        setGebruikersnaam(gebruikersnaam);
        setCurrentProfiel(gebruikersprofiel);
        setCurrentStatus(gebruikersstatus);
        setProfielfoto("storage/profielfotos/profielfoto.png");
        setAantalInlogPogingen(aantalInlogPogingen);
        setWachtwoord(wachtwoord);
    }

    /**
     * Constructor voor aanmaken van een gebruiker zonder profielfoto
     *
     * @param naam              (String) ==> Naam van de gebruiker
     * @param gebruikersnaam    (String) ==> Februikersnaam van de gebruiker voor het Chamillo platform
     * @param gebruikersprofiel (Gebruikersprofiel) ==> Profiel dat de gebruiker aanneemt in het algemeen
     * @param gebruikersstatus  (Gebruikersprofiel) ==> Inlogstatus van de gebruiker
     */
    public Gebruiker(String naam, String gebruikersnaam, Gebruikersprofiel gebruikersprofiel, Gebruikersstatus gebruikersstatus) {
        this(naam, gebruikersnaam, gebruikersprofiel, gebruikersstatus, "storage/profielfotos/profielfoto.png", 0, null);
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
                Arrays.stream(Gebruikersprofiel.values()).filter(g -> g.toString().equals(gebruikersprofiel)).findFirst().orElse(null),
                Arrays.stream(Gebruikersstatus.values()).filter(g -> g.toString().equals(gebruikersstatus)).findFirst().orElse(null),
                "storage/profielfotos/profielfoto.png", 0, null);
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
        this(naam, gebruikersnaam,
                Arrays.stream(Gebruikersprofiel.values()).filter(g -> g.toString().equals(gebruikersprofiel)).findFirst().orElse(null),
                Arrays.stream(Gebruikersstatus.values()).filter(g -> g.toString().equals(gebruikersstatus)).findFirst().orElse(null),
                profielfoto, 0, wachtwoord);
    }
    //endregion

    //region Setters
    private void setProfielfoto(String path) {
        File file = new File("storage/profielfotos/profielfoto.png");
        byte[] bFile = new byte[(int) file.length()];
        this.profielfoto = bFile;
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

    private void setCurrentProfiel(String gebruikersprofiel){
        setCurrentProfiel(Arrays.stream(Gebruikersprofiel.values()).filter(p -> p.toString().equals(gebruikersprofiel)).findFirst().orElse(null));
    }

    private void setCurrentProfiel(Gebruikersprofiel gebruikersprofiel) {
        if (gebruikersprofiel == null){
            gebruikersprofiel = Gebruikersprofiel.GEBRUIKER;
        }
        switch (gebruikersprofiel){
            case HOOFDVERANTWOORDELIJKE:
                toProfielState(new HoofdverantwoordelijkeState(this));
                break;
            case VERANTWOORDELIJKE:
                toProfielState(new VerantwoordelijkeState(this));
                break;
            default:
            case GEBRUIKER:
                toProfielState(new GebruikerState(this));
                break;
        }
    }

    private void toProfielState(GebruikerProfielState profielState) {
        currentProfiel = profielState;
    }

    private void setCurrentStatus(String gebruikersStatus){
        setCurrentStatus(Arrays.stream(Gebruikersstatus.values()).filter(p -> p.toString().equals(gebruikersStatus)).findFirst().orElse(null));
    }

    private void setCurrentStatus(Gebruikersstatus status) {
        if(status == null){
            status = Gebruikersstatus.NIET_ACTIEF;
        }
        switch (status){
            case ACTIEF:
                toStatusState(new ActiefStatusState(this));
                break;
            case GEBLOKKEERD:
                toStatusState(new GeblokkeerdStatusState(this));
                break;
            default:
            case NIET_ACTIEF:
                toStatusState(new NietActiefStatusState(this));
                break;
        }
    }

    private void toStatusState(GebruikerStatusState statusState) {
        this.currentStatus = statusState;
    }

    public void setVerwijderd(boolean verwijderd) {
        this.verwijderd = verwijderd;
    }

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

    public void setWachtwoord(String wachtwoord) {
        this.wachtwoord = wachtwoord;
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
    public byte[] getProfielfoto() {
        return profielfoto;
    }

    @Override
    public LocalDateTime getLaatstIngelogd() {
        return laatstIngelogd;
    }

    @Override
    public String getWachtwoord() {
        return wachtwoord;
    }

    //endregion

    //region methodes

    /**
     *
     * @param gegevens (String naam, String gebruikersnaam, String profiel, String Status)
     */
    public void update(List<String> gegevens){
        try {
            if (gegevens.get(0) != null && !gegevens.get(0).isBlank()) {
                setNaam(gegevens.get(0));
            }
            if (gegevens.get(1) != null && !gegevens.get(1).isBlank()) {
                setGebruikersnaam(gegevens.get(1));
            }
            if (gegevens.get(2) != null && !gegevens.get(2).isBlank()) {
                setCurrentProfiel(gegevens.get(2));
            }
            if (gegevens.get(3) != null && !gegevens.get(3).isBlank()) {
                setCurrentStatus(gegevens.get(3));
            }
        } catch (Exception e){
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
