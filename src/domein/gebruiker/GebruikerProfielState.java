package domein.gebruiker;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class GebruikerProfielState implements Serializable {
    private static final long serialVersionUID = -8136365267378240163L;
    //@Id
    //protected String profielId;
    @Id
    protected String gebruikersId;
    @Id
    protected String profiel;
    @OneToOne()
    protected Gebruiker gebruiker;


    public GebruikerProfielState() {
    }

    protected GebruikerProfielState(String profiel, Gebruiker gebruiker) {
        //this.profielId = String.format("%s_%s", profiel, gebruiker.getGebruikersnaam());
        this.profiel = profiel;
        this.gebruiker = gebruiker;
        this.gebruikersId = this.gebruiker.getGebruikersnaam();
    }

    public String getProfiel(){
        return this.profiel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GebruikerProfielState that = (GebruikerProfielState) o;
        return gebruikersId.equals(that.gebruikersId) &&
                profiel.equals(that.profiel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gebruikersId, profiel);
    }
}
