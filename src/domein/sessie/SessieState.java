package domein.sessie;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

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

    public abstract void update(List<Object> gegevens);

    public abstract void verwijder(boolean verwijder);

    @Override
    public String toString() {
        return getStatus().toLowerCase();
    }
}
