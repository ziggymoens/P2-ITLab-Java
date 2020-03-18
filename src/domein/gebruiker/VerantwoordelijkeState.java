package domein.gebruiker;

public class VerantwoordelijkeState extends GebruikerProfielState {
    public VerantwoordelijkeState(Gebruiker gebruiker) {
        super("VERANTWOORDELIJKE", gebruiker);
    }
}
