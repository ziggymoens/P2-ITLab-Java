package domein.sessie;

import javax.persistence.*;

@Entity
@Table(name = "geslotenState")
public class GeslotenState extends SessieState {
    public GeslotenState() {
    }

    public GeslotenState(Sessie sessie) {
        super("GESLOTEN", sessie);
    }
}
