package domein;

import domein.interfacesDomein.IAcademiejaar;
import domein.interfacesDomein.ISessie;
import domein.sessie.Sessie;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@Table(name = "academiejaar")
public class Academiejaar implements IAcademiejaar {
    @Id
    private int academiejaar;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "academiejaar", fetch = FetchType.LAZY)
    private List<Sessie> sessies;

    private LocalDate start;
    private LocalDate eind;

    @Transient
    private String academiejaarString;
    @Transient
    private String startString;
    @Transient
    private String eindString;
    @Transient
    private int aantal;

    protected Academiejaar() {
    }

    public Academiejaar(int aj, LocalDate start, LocalDate eind) {
        this.academiejaar = aj;

        this.start = start;
        this.eind = eind;

    }

    public void initTable(){
        setAcademiejaarString();
        setDateString();
    }

    @Override
    public int getAcademiejaar() {
        return academiejaar;
    }

    @Override
    public List<ISessie> getSessiesO() {
        return (List<ISessie>) (Object) sessies;
    }

    @Override
    public LocalDate getStart() {
        return start;
    }

    @Override
    public LocalDate getEind() {
        return eind;
    }

    @Override
    public String getAcademiejaarString(){
        return academiejaarString;
    }

    @Override
    public String getStartString() {
        return startString;
    }

    @Override
    public String getEindString() {
        return eindString;
    }

    @Override
    public int getAantal () {
        return sessies.size();
    }

    private void setAcademiejaarString(){
        StringBuilder sb = new StringBuilder();
        String j1 = ((Integer)academiejaar).toString().substring(0,2);
        String j2 = ((Integer)academiejaar).toString().substring(2);
        sb.append("20" + j1);
        sb.append(" - ");
        sb.append("20" + j2);
        academiejaarString = sb.toString();
    }
    private void setDateString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM");
        this.startString = start.format(dtf);
        this.eindString = eind.format(dtf);
    }
}
