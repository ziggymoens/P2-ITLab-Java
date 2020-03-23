package domein.sessie;

import exceptions.domein.SessieException;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.List;

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

    @Override
    public void update(List<Object> gegevens) {
        throw new SessieException("State;Deze sessie kan niet worden gewijzigd aangezien deze gesloten is.");
    }

    @Override
    public void verwijder(boolean verwijder) {
        throw new SessieException("State;Deze sessie kan niet worden verwijderd aangezien deze gesloten is.");
    }
}
