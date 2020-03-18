package domein.gebruiker;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "gebruikerStatus")
@Table(name = "gebruikersstatus")
public abstract class GebruikerStatusState implements Serializable{
    private static final long serialVersionUID = 8149175523928193745L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int statusId;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    protected Gebruiker gebruiker;

    public GebruikerStatusState() {
    }

    public GebruikerStatusState(Gebruiker gebruiker) {
        this.gebruiker = gebruiker;
    }

    public abstract String getStatus();

}