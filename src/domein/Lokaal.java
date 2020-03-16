package domein;

import domein.enums.Gebouwen;
import domein.enums.LokaalType;
import domein.interfacesDomein.ILokaal;
import exceptions.domein.LokaalException;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
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
    @Enumerated(EnumType.STRING)
    private Gebouwen gebouw;
    @Transient
    private int verdieping;
    @Enumerated(EnumType.STRING)
    private LokaalType type;
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
        setLokaalGegevens();
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
        this.type = Arrays.stream(LokaalType.values()).filter(t -> t.toString().equals(type)).findFirst().orElse(null);
    }

    private void setLokaalGegevens() {
        String gebouw = lokaalCode.substring(4,5);
        switch (gebouw){
            case "B":
                if (lokaalCode.substring(1,4).equals("SCH")){
                    this.gebouw = Gebouwen.B_G;
                }else{
                    this.gebouw = Gebouwen.B_A;
                }
                break;
            case "C":
                this.gebouw = Gebouwen.C;
                break;
            case "D":
                this.gebouw = Gebouwen.D;
                break;
            case "P":
                this.gebouw = Gebouwen.P;
                break;
            default:
                break;
        }
        this.verdieping =  Integer.parseInt(lokaalCode.substring(5,6));
    }
    //endregion

    //region Getters
    @Override
    public String getLokaalCode() {
        return lokaalCode;
    }
    @Override
    public String getType(){
        return type.toString();
    }
    @Override
    public int getAantalPlaatsen() {
        return aantalPlaatsen;
    }
    @Override
    public String getStad(){
        return gebouw.getCampus().getStad().toString();
    }
    @Override
    public String getGebouw() {
        return gebouw.toString();
    }
    @Override
    public int getVerdieping(){
        return this.verdieping;
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

    public void update(List<Object> gegevens) {
        try {
            if (gegevens.get(0) != null && !((String) gegevens.get(0)).isBlank()) {
                setLokaalCode((String) gegevens.get(0));
            }
            if (gegevens.get(1) != null && !((String) gegevens.get(1)).isBlank()) {
                setLokaalType((String) gegevens.get(1));
            }
            if (gegevens.get(2) != null) {
                setAantalPlaatsen((Integer) gegevens.get(2));
            }
            setLokaalGegevens();
        }catch (Exception e){
            throw new LokaalException("Update");
        }
    }
    //endregion
}
