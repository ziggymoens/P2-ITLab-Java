package domein.gebruiker;

import exceptions.domein.GebruikerException;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "gebruikerStatus")
public abstract class GebruikerStatusState implements Serializable{
    private static final long serialVersionUID = 8149175523928193745L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int statusId;

    private String status;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    protected Gebruiker gebruiker;

    public GebruikerStatusState() {
    }

    protected GebruikerStatusState(String status, Gebruiker gebruiker) {
        this.gebruiker = gebruiker;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}