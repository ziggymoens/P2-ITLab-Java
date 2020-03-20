package domein.sessie;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "sessieStatus")
@Table(name = "sessiestatus")
public abstract class SessieState implements Serializable {

    private static final long serialVersionUID = -3649948815265095922L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    protected Sessie sessie;


    protected SessieState() {
    }

    protected SessieState(Sessie sessie) {
        this.sessie = sessie;
    }

    public abstract String getStatus();

    @Override
    public String toString() {
        return getStatus().toLowerCase();
    }
}
