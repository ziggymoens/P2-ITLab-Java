package domein;

import exceptions.domein.LokaalException;

import java.io.Serializable;
import java.util.Objects;

public class Lokaal implements Serializable {
    //region Variabelen
    //Primairy key
    private String lokaalCode;

    private int aantalPlaatsen;
    //endregion

    //region Constructor
    public Lokaal(String lokaalCode, int aantalPlaatsen) {
        setLokaalCode(lokaalCode);
        setAantalPlaatsen(aantalPlaatsen);
    }
    //endregion

    //region Setters
    private void setLokaalCode(String lokaalCode) {
        if (lokaalCode == null || lokaalCode.isBlank()){
            throw new LokaalException("LokaalException.lokaalCode");
        }
        this.lokaalCode = lokaalCode;
    }

    private void setAantalPlaatsen(int aantalPlaatsen) {
        if(aantalPlaatsen < 0){
            throw new LokaalException("LokaalException.aantalPlaatsen");
        }
        this.aantalPlaatsen = aantalPlaatsen;
    }
    //endregion

    //region Getters
    public String getLokaalCode() {
        return lokaalCode;
    }

    public int getAantalPlaatsen() {
        return aantalPlaatsen;
    }
    //endregion

    //region toString
    @Override
    public String toString() {
        return String.format("%s, %dp", this.lokaalCode, this.aantalPlaatsen);
    }
    //endregion

    //region Equals & Hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lokaal lokaal = (Lokaal) o;
        return Objects.equals(lokaalCode, lokaal.lokaalCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lokaalCode);
    }
    //endregion
}
