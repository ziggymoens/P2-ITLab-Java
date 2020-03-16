package domein.sessie;

import javax.persistence.*;

@Entity
@Table(name = "openState")
public class OpenState extends SessieState {
    public OpenState() {
    }

    public OpenState(Sessie sessie) {
        super("OPEN", sessie);
    }
}
