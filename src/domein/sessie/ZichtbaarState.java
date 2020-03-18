package domein.sessie;

import javax.persistence.*;

@Entity
@DiscriminatorValue("ZICHTBAAR")
public class ZichtbaarState extends SessieState {

    private static final long serialVersionUID = 5423012923739736619L;

    public ZichtbaarState() {
    }

    public ZichtbaarState(Sessie sessie) {
        super(sessie);
    }

    @Override
    public String getStatus() {
        return "ZICHTBAAR";
    }
}
