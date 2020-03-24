package domein.sessie;

import exceptions.domein.SessieException;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("zichtbaar")
public class ZichtbaarState extends SessieState {

    private static final long serialVersionUID = 5423012923739736619L;

    public ZichtbaarState() {
    }

    public ZichtbaarState(Sessie sessie) {
        super(sessie);
    }

    @Override
    public String getStatus() {
        return "zichtbaar";
    }

    @Override
    public void update(List<Object> gegevens) {
        throw new SessieException("State;Deze sessie kan niet worden gewijzigd aangezien deze zichtbaar is.");
    }

    @Override
    public void verwijder(boolean verwijder) {
        throw new SessieException("State;Deze sessie kan niet worden verwijderd aangezien deze zichtbaar is.");
    }
}
