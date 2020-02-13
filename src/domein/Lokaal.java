package domein;

import exceptions.LokaalException;

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
}
