package domein.sessie;

import javax.persistence.*;

@Entity
@Table(name = "nietZichtbaarState")
public class NietZichtbaarState extends SessieState {
    public NietZichtbaarState() {
    }

    public NietZichtbaarState(Sessie sessie) {
        super("NIET ZICHTBAAR", sessie);
    }
}
