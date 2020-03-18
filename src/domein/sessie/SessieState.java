package domein.sessie;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class SessieState implements Serializable {
    private static final long serialVersionUID = -8721759520484436793L;
    @Id
    protected String sessieId;
    @Id
    protected String status;
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    protected Sessie sessie;

    protected SessieState(){}

    protected SessieState(String status,Sessie sessie) {
//        this.statusId = String.format("%s_%s", status, sessie.getSessieId());
        this.status = status;
        this.sessie = sessie;
        this.sessieId = this.sessie.getSessieId();
    }

    public String getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessieState that = (SessieState) o;
        return sessieId.equals(that.sessieId) &&
                status.equals(that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessieId, status);
    }
}
