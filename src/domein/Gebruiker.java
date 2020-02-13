package domein;

import exceptions.GebruikerException;

public class Gebruiker {
    private String profielfoto;
    private String naam;
    private String gebruikersnaam;
    private Gebruikersprofielen type;
    private Gebruikersstatus status;

    public Gebruiker(String naam, String gebruikersnaam, Gebruikersprofielen type, Gebruikersstatus status) {
        setNaam(naam);
        setGebruikersnaam(gebruikersnaam);
        setType(type);
        setStatus(status);
    }

    public Gebruiker(String profielfoto, String naam, String gebruikersnaam, Gebruikersprofielen type, Gebruikersstatus status) {
        setNaam(naam);
        setGebruikersnaam(gebruikersnaam);
        setType(type);
        setStatus(status);
        setProfielfoto(profielfoto);
    }

    private void setProfielfoto(String profielfoto) {
        if (profielfoto == null || profielfoto.isBlank()){
            throw new GebruikerException();
        }
        this.profielfoto = profielfoto;
    }

    private void setNaam(String naam) {
        if(naam == null || naam.isBlank()){
            throw new GebruikerException();
        }
        this.naam = naam;
    }

    private void setGebruikersnaam(String gebruikersnaam) {
        if (gebruikersnaam == null || gebruikersnaam.isBlank()){
            throw new GebruikerException();
        }
        this.gebruikersnaam = gebruikersnaam;
    }

    private void setType(Gebruikersprofielen type) {
        this.type = type;
    }

    private void setStatus(Gebruikersstatus status) {
        this.status = status;
    }

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
}
