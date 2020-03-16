package domein.sessie;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class SessieState {
    @Id
    protected String statusId;
    private String status;
    @OneToOne
    protected Sessie sessie;

    protected SessieState(){}

    protected SessieState(String status,Sessie sessie) {
        this.statusId = String.format("%s_%s", status, sessie.getSessieId());
        this.status = status;
        this.sessie = sessie;
    }

    public String getStatus() {
        return status;
    }
}
