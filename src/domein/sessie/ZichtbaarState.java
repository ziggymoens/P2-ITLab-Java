package domein.sessie;

import javax.persistence.*;

@Entity
@Table(name = "zichtbaarState")
public class ZichtbaarState extends SessieState {
    public ZichtbaarState() {
    }

    public ZichtbaarState(Sessie sessie) {
        super("ZICHTBAAR", sessie);
    }
}
