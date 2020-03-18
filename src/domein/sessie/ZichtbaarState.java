package domein.sessie;

import javax.persistence.*;

public class ZichtbaarState extends SessieState {
    public ZichtbaarState(Sessie sessie) {
        super("ZICHTBAAR", sessie);
    }

}
