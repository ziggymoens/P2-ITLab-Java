package domein;

import domein.interfacesDomein.IGebruiker;
import exceptions.domein.GebruikerException;
import language.Talen;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Entity
//@Table(name = "gebruiker")
public class Gebruiker implements IGebruiker {

    //region Variabelen
    //Primairy key
    @Id
    private String gebruikersnaam;

    private String profielfoto;
    private String naam;
    private Gebruikersprofielen gebruikersprofiel;
    private Gebruikersstatus status;
    @OneToMany
    private List<Feedback> feedbackList;
    @OneToMany
    private List<Aankondiging> aankondigingList;
    @OneToMany
    private List<Inschrijving> inschrijvingList;
    @OneToMany
    private List<Media> mediaList;
    //@OneToMany
    //private List<Sessie> sessieVerantwoordlijkList;
    //endregion

    //region Constructor
    protected Gebruiker(){}

    public Gebruiker(String naam, String gebruikersnaam, Gebruikersprofielen gebruikersprofiel, Gebruikersstatus status, String profielfoto) {
        setNaam(naam);
        setGebruikersnaam(gebruikersnaam);
        setGebruikersprofielen(gebruikersprofiel);
        setStatus(status);
        setProfielfoto(profielfoto);
    }

    public Gebruiker(String naam, String gebruikersnaam, Gebruikersprofielen gebruikersprofiel, Gebruikersstatus status) {
        this(naam, gebruikersnaam, gebruikersprofiel, status, null);
    }

    public Gebruiker(String naam, String gebruikersnaam, String gebruikersprofiel, String gebruikersstatus) {
        setNaam(naam);
        setGebruikersnaam(gebruikersnaam);
        setGebruikersprofielen(genereerProfiel(gebruikersprofiel));
        setStatus(genereerStatus(gebruikersstatus));
        setProfielfoto(null);
    }

    public Gebruiker(String naam, String gebruikersnaam, String gebruikersprofiel, String gebruikersstatus, String profielfoto) {
        setNaam(naam);
        setGebruikersnaam(gebruikersnaam);
        setGebruikersprofielen(genereerProfiel(gebruikersprofiel));
        setStatus(genereerStatus(gebruikersstatus));
        setProfielfoto(profielfoto);
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
        if(gebruikersprofielen == null)
            throw new GebruikerException();
        if (Arrays.stream(Gebruikersprofielen.values()).filter(e -> e  == gebruikersprofielen).findFirst().orElse(null) == null) {
            throw new GebruikerException("GebruikerException.verantwoordelijkeFoutType");
        }
        this.gebruikersprofiel = gebruikersprofielen;
    }

    private void setStatus(Gebruikersstatus status) {
        if(status == null)
            throw new GebruikerException();
        if (Arrays.stream(Gebruikersstatus.values()).filter(e -> e  == status).findFirst().orElse(null) == null) {
            throw new GebruikerException("GebruikerException.verantwoordelijkeFoutStatus");
        }
        this.status = status;
    }
    //endregion

    //region Getters
    public String getProfielfoto() {
        return profielfoto;
    }

    public String getNaam() {
        return naam;
    }

    public String getGebruikersnaam() {
        return gebruikersnaam;
    }

    public Gebruikersprofielen getGebruikersprofielen() {
        return gebruikersprofiel;
    }

    public Gebruikersstatus getStatus() {
        return status;
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


    private Gebruikersstatus genereerStatus(String gebruikersstatus) {
        return Arrays.stream(Gebruikersstatus.values()).filter(g -> g.toString().equals(gebruikersstatus)).findFirst().orElse(null);
    }

    private Gebruikersprofielen genereerProfiel(String gebruikersprofiel) {
        return Arrays.stream(Gebruikersprofielen.values()).filter(g -> g.toString().equals(gebruikersprofiel)).findFirst().orElse(null);
    }
}
