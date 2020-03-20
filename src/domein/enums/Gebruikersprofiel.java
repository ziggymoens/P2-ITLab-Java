package domein.enums;

/**
 * Deze enum bevat de mogelijke gebruikerspofielen die de gebruikers kunnen aannemen
 */
public enum Gebruikersprofiel {
    GEBRUIKER, VERANTWOORDELIJKE, HOOFDVERANTWOORDELIJKE;

    public String toLowerCase(){
        return this.toString().toLowerCase();
    }
}
