package domein.domeinklassen;

import domein.interfacesDomein.ISessie;
import domein.interfacesDomein.ISessieKalender;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "sessieKalender")
public class SessieKalender implements ISessieKalender {

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

    @Override
    public int getAcademiajaar() {
        return academiajaar;
    }

    @Override
    public List<ISessie> getISessieList(){
        return (List<ISessie>)(Object) sessieList.stream().sorted(new Comparator<Sessie>() {
            @Override
            public int compare(Sessie o1, Sessie o2) {
                return o1.getStartSessie().compareTo(o2.getStartSessie());
            }
        }).collect(Collectors.toList());
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

    @Override
    public String toString() {
        return String.format("Academiejaar %s - %s", String.valueOf(getAcademiajaar()).substring(0,2), String.valueOf(getAcademiajaar()).substring(2,4));
    }

}
