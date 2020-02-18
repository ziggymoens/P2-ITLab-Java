package domein;

import exceptions.domein.GebruikerException;
import language.Talen;
import test.domein.GebruikerTest;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class Gebruiker implements Serializable {
    private static final long serialVersionUID = -5444941573344748602L;

    //region Variabelen
    //Primairy key
    private String gebruikersnaam;

    private String profielfoto;
    private String naam;
    private Gebruikersprofielen type;
    private Gebruikersstatus status;
    //endregion

    //region Constructor
    public Gebruiker(String naam, String gebruikersnaam, Gebruikersprofielen type, Gebruikersstatus status) {
        this(naam, gebruikersnaam, type, status, null);
    }

    public Gebruiker(String naam, String gebruikersnaam, Gebruikersprofielen type, Gebruikersstatus status, String profielfoto) {
        setNaam(naam);
        setGebruikersnaam(gebruikersnaam);
        setType(type);
        setStatus(status);
        setProfielfoto(profielfoto);
    }

    public Gebruiker(String naam, String gebruikersnaam, String gebruikersprofiel, String gebruikerstype) {
        setNaam(naam);
        setGebruikersnaam(gebruikersnaam);
        setType((Gebruikersprofielen) generateType("PROFIEL", gebruikersprofiel));
        setStatus((Gebruikersstatus) generateType("TYPE", gebruikerstype));
        setProfielfoto(null);
    }

    public Gebruiker(String naam, String gebruikersnaam, String gebruikersprofiel, String gebruikerstype, String profielfoto) {
        setNaam(naam);
        setGebruikersnaam(gebruikersnaam);
        setType((Gebruikersprofielen) generateType("PROFIEL", gebruikersprofiel));
        setStatus((Gebruikersstatus) generateType("TYPE", gebruikerstype));
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

    private void setType(Gebruikersprofielen type) {
        if (Arrays.stream(Gebruikersprofielen.values()).filter(e -> e  == type).findFirst().orElse(null) == null) {
            throw new GebruikerException("GebruikerException.verantwoordelijkeFoutType");
        }
        this.type = type;
    }

    private void setStatus(Gebruikersstatus status) {
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

    public Gebruikersprofielen getType() {
        return type;
    }

    public Gebruikersstatus getStatus() {
        return status;
    }
    //endregion

    //region toString
    @Override
    public String toString() {
        return String.format("%s: %s%n%s: %s%n%s: %s%n%s: %s%n",
                Talen.getString("Gebruiker.naam"), naam, Talen.getString("Gebruiker.gebruikersnaam"), gebruikersnaam, Talen.getString("Gebruiker.type"), type, Talen.getString("Gebruiker.status"), status);
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


    private Object generateType(String k, String naam) {
        switch (k.toUpperCase()){
            case "TYPE":
                return Arrays.stream(Gebruikersstatus.values()).filter(g -> g.toString().equals(naam.toUpperCase())).findFirst().orElse(null);
            case "PROFIEL":
                return Arrays.stream(Gebruikersprofielen.values()).filter(g -> g.toString().equals(naam.toUpperCase())).findFirst().orElse(null);
            default:
                return null;
        }
    }
}
