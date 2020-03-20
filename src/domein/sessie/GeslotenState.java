package domein.sessie;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("GESLOTEN")
public class GeslotenState extends SessieState {

    private static final long serialVersionUID = -3133496800896038854L;

    protected GeslotenState() {
    }

    public GeslotenState(Sessie sessie) {
        super(sessie);
    }

    @Override
    public String getStatus() {
        return "gesloten";
    }
}
