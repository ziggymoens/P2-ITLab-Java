package domein;

import domein.sessie.Sessie;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "academiejaar")
public class Academiejaar {
    @Id
    private int academiejaar;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "academiejaar", fetch = FetchType.LAZY)
    private List<Sessie> sessies;

    private LocalDate start;
    private LocalDate eind;

    protected Academiejaar() {
    }

    public Academiejaar(int aj, LocalDate start, LocalDate eind) {
        this.academiejaar = aj;
        this.start = start;
        this.eind = eind;
    }

    public int getCode() {
        return academiejaar;
    }
}
