package domein.gebruiker;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class GebruikerProfielState {
    @Id
    protected String profielId;
    private String profiel;
    @OneToOne()
    protected Gebruiker gebruiker;

    public GebruikerProfielState() {
    }

    protected GebruikerProfielState(String profiel, Gebruiker gebruiker) {
        this.profielId = String.format("%s_%s", profiel, gebruiker.getGebruikersnaam());
        this.profiel = profiel;
        this.gebruiker = gebruiker;
    }

    public String getProfiel(){
        return this.profiel;
    }
}
