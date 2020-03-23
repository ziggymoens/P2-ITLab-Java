package domein.sessie;

import exceptions.domein.SessieException;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.List;

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
        return "open";
    }

    @Override
    public void update(List<Object> gegevens) {
        throw new SessieException("State;Deze sessie kan niet worden gewijzigd aangezien deze open is.");
    }

    @Override
    public void verwijder(boolean verwijder) {
        throw new SessieException("State;Deze sessie kan niet worden verwijderd aangezien deze open is.");
    }
}
