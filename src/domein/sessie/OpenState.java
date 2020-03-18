package domein.sessie;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("OPEN")
public class OpenState extends SessieState {
    private static final long serialVersionUID = -7595112207685499466L;

    public OpenState() {
    }

    public OpenState(Sessie sessie) {
        super(sessie);
    }

    @Override
    public String getStatus() {
        return "OPEN";
    }
}
