package domein.sessie;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "sessieStatus")
public abstract class SessieState implements Serializable {

    private static final long serialVersionUID = -3649948815265095922L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    private String status;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    protected Sessie sessie;


    protected SessieState() {
    }

    protected SessieState(String status, Sessie sessie) {
        this.sessie = sessie;
        this.status = status;
    }

    public String getStatus(){
        return status;
    }
}
