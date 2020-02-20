package domein;

import exceptions.domein.SessieKalenderException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SessieKalenderController {
    //region Variabelen
    //Primairy key
    private String sessieKalenderId;

    private List<Sessie> sessieList;
    private String naamKalender;
    //endregion

    //region Constructor
    public SessieKalenderController(String sessieKalenderId, String naamKalender) {
        setSessieKalenderId(sessieKalenderId);
        setNaamKalender(naamKalender);
        sessieList = new ArrayList<>();
    }
    //endregion

    //region Setters
    public void setSessieKalenderId(String sessieKalenderId) {
        if(sessieKalenderId == null || sessieKalenderId.isBlank()){
            throw new SessieKalenderException();
        }
        this.sessieKalenderId = sessieKalenderId;
    }

    public void setNaamKalender(String naamKalender) {
        if(naamKalender == null || naamKalender.isBlank()){
            throw new SessieKalenderException();
        }
        this.naamKalender = naamKalender;
    }
    //endregion

    //region Getters

    public String getSessieKalenderId() {
        return sessieKalenderId;
    }

    public List<Sessie> getSessieList() {
        return sessieList;
    }

    public String getNaamKalender() {
        return naamKalender;
    }

    //endregion

    //region sessies toevoegen, verwijderen, opvragen

    public void voegSessieToe(Sessie s){
        if (s == null){
            throw new SessieKalenderException();
        }
        sessieList.add(s);
    }

    public Sessie geefSessieVanKalender(int sId){
        return sessieList.stream().filter(s -> s.getSessieId() == sId).findFirst().orElse(null);
    }

    public void verwijderSessieVanKalender(Sessie s){
        sessieList.remove(s);
    }

    //endregion

    //region Equals & hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessieKalenderController that = (SessieKalenderController) o;
        return Objects.equals(sessieKalenderId, that.sessieKalenderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessieKalenderId);
    }
    //endregion
}
