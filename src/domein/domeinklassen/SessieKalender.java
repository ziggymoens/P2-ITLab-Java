package domein.domeinklassen;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sessieKalender")
public class SessieKalender {

    @Id
    private int academiajaar;

    @OneToMany
    List<Sessie> sessieList;

    public SessieKalender(){
    }

    public SessieKalender(int academiajaar) {
        this.academiajaar = academiajaar;
        sessieList = new ArrayList<>();
    }

    private void setAcademiajaar(int academiajaar) {
        this.academiajaar = academiajaar;
    }

    private void addSessie(Sessie sessie) {
        sessieList.add(sessie);
    }

    public int getAcademiajaar() {
        return academiajaar;
    }

    public List<Sessie> getSessieList() {
        return sessieList;
    }

    public Sessie geefSessie(String sessieId){
        return sessieList.stream().filter(s -> s.getSessieId().equals(sessieId)).findFirst().orElse(null);
    }

    public void addSessies(List<Sessie> sessies) {
        this.sessieList.addAll(sessies);
    }
}
