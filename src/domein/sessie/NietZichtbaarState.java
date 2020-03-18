package domein.sessie;

public class NietZichtbaarState extends SessieState {
    public NietZichtbaarState(Sessie sessie) {
        super("NIET ZICHTBAAR", sessie);
    }

}
