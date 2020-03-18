package domein.gebruiker;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "gebruikerProfiel")
@Table(name = "gebruikersprofiel")
public abstract class GebruikerProfielState implements Serializable {
    private static final long serialVersionUID = -8136365267378240163L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int profielId;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    protected Gebruiker gebruiker;


    public GebruikerProfielState() {
    }

    public GebruikerProfielState(Gebruiker gebruiker) {
        this.gebruiker = gebruiker;
    }

    public abstract String getProfiel();
}
