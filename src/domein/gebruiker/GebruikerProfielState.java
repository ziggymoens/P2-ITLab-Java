package domein.gebruiker;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "gebruikerProfiel")
public abstract class GebruikerProfielState implements Serializable {
    private static final long serialVersionUID = -8136365267378240163L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int profielId;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    protected Gebruiker gebruiker;

    private String profiel;


    public GebruikerProfielState() {
    }

    protected GebruikerProfielState(String profiel, Gebruiker gebruiker) {
        this.profiel = profiel;
        this.gebruiker = gebruiker;
    }

    public String getProfiel(){
        return profiel;
    }
}
