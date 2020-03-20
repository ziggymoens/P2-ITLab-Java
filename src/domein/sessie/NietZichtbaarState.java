package domein.sessie;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("NIET_ZICHTBAAR")
public class NietZichtbaarState extends SessieState {
    private static final long serialVersionUID = -5986113834670431580L;

    protected NietZichtbaarState() {
    }

    public NietZichtbaarState(Sessie sessie) {
        super(sessie);
    }

    @Override
    public String getStatus() {
        return "niet zichtbaar";
    }


}
