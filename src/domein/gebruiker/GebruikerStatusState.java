package domein.gebruiker;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class GebruikerStatusState implements Serializable {
    private static final long serialVersionUID = 8149175523928193745L;

//    @Id
//    protected String statusId;
    @Id
    protected String gebruikersId;
    @Id
    protected String status;
    @OneToOne
    protected Gebruiker gebruiker;

    public GebruikerStatusState() {
    }

    protected GebruikerStatusState(String status, Gebruiker gebruiker) {
//        this.statusId = String.format("%s_%s", status, gebruiker.getGebruikersnaam());
        this.status = status;
        this.gebruiker = gebruiker;
        this.gebruikersId = this.gebruiker.getGebruikersnaam();
    }

    public String getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GebruikerStatusState that = (GebruikerStatusState) o;
        return gebruikersId.equals(that.gebruikersId) &&
                status.equals(that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gebruikersId, status);
    }
}
