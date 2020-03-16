package domein.gebruiker;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class GebruikerStatusState {
    @Id
    protected String statusId;
    private String status;
    @OneToOne
    protected Gebruiker gebruiker;

    public GebruikerStatusState() {
    }

    protected GebruikerStatusState(String status, Gebruiker gebruiker) {
        this.statusId = String.format("%s_%s", status, gebruiker.getGebruikersnaam());
        this.status = status;
        this.gebruiker = gebruiker;
    }

    public String getStatus() {
        return status;
    }
}
