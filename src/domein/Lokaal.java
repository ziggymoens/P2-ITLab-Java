package domein;

import exceptions.LokaalException;

import java.util.Objects;

public class Lokaal {
    private String lokaalCode;
    private int aantalPlaatsen;

    public Lokaal(String lokaalCode, int aantalPlaatsen) {
        setLokaalCode(lokaalCode);
        setAantalPlaatsen(aantalPlaatsen);
    }

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

    public String getLokaalCode() {
        return lokaalCode;
    }

    public int getAantalPlaatsen() {
        return aantalPlaatsen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lokaal)) return false;
        Lokaal lokaal = (Lokaal) o;
        return aantalPlaatsen == lokaal.aantalPlaatsen &&
                lokaalCode.equals(lokaal.lokaalCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lokaalCode, aantalPlaatsen);
    }
}
