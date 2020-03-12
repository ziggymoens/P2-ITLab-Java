package domein;

import domein.enums.LokaalTypes;
import domein.interfacesDomein.ILokaal;
import exceptions.domein.LokaalException;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "lokaal")
public class Lokaal implements ILokaal {

    //region Variabelen
    //Primairy key
    @Id
    private String lokaalCode;

    private int aantalPlaatsen;
    private boolean verwijderd = false;
    @Transient
    private String stad;
    @Transient
    private String gebouw;
    @Transient
    private String verdieping;
    private LokaalTypes type;
    //endregion

    //region Constructor

    /**
     * Constructor voor JPA
     */
    protected Lokaal() {
    }

    /**
     * Default constructor voor Lokaal
     *
     * @param lokaalCode     (String) ==> de code van het lokaal
     * @param aantalPlaatsen (int) ==> de maximale capaciteit van het lokaal
     */
    public Lokaal(String lokaalCode, String type, int aantalPlaatsen) {
        setLokaalCode(lokaalCode);
        setLokaalType(type);
        setAantalPlaatsen(aantalPlaatsen);
    }
    //endregion

    //region Setters
    private void setLokaalCode(String lokaalCode) {
        if (lokaalCode == null || lokaalCode.isBlank()) {
            throw new LokaalException("LokaalException.lokaalCode");
        }
        this.lokaalCode = lokaalCode;
    }

    private void setAantalPlaatsen(int aantalPlaatsen) {
        if (aantalPlaatsen < 0) {
            throw new LokaalException("LokaalException.aantalPlaatsen");
        }
        this.aantalPlaatsen = aantalPlaatsen;
    }

    public void setVerwijderd(boolean verwijderd) {
        this.verwijderd = verwijderd;
    }

    public void setLokaalType(String type) {
        this.type = Arrays.stream(LokaalTypes.values()).filter(t -> t.toString().equals(type)).findFirst().orElse(null);
    }
    private void setVerdieping() {
        if (!lokaalCode.startsWith("G")){
            this.verdieping = "Onbekend";
        } else { this.verdieping =  lokaalCode.substring(5,6);}
    }

    private void setGebouw() {
        if (!lokaalCode.startsWith("G")){
            this.gebouw =  "Onbekend";
        } else{ this.gebouw = lokaalCode.substring(4,5);}
    }

    private void setStad() {
        if (!lokaalCode.startsWith("G")){
            this.stad = "Onbekend";
        } else if(lokaalCode.matches("GS(.)+")){
            this.stad = "Gent";
        } else if(lokaalCode.matches("GA(.)+")){
            this.stad =  "Aalst";
        }
    }

    //endregion

    //region Getters
    public String getLokaalCode() {
        return lokaalCode;
    }

    public String getType(){
        return type.toString();
    }

    public int getAantalPlaatsen() {
        return aantalPlaatsen;
    }

    public String getStad(){
        setStad();
        return stad;
    }

    public String getGebouw(){
        setGebouw();
        return gebouw;
    }

    public String getVerdieping(){
        setVerdieping();
        return verdieping;
    }
    //endregion

    //region toString
    @Override
    public String toString() {
        return String.format("%s, %dp.", this.lokaalCode, this.aantalPlaatsen);
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
