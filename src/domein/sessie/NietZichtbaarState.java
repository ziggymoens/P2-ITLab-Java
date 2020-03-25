package domein.sessie;

import domein.Lokaal;
import domein.gebruiker.Gebruiker;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@DiscriminatorValue("niet zichtbaar")
public class NietZichtbaarState extends SessieState {
    private static final long serialVersionUID = -5986113834670431580L;

    protected NietZichtbaarState() {
    }

    public NietZichtbaarState(Sessie sessie) {
        super(sessie);
    }

    @Override
    public String getStatus() {
        return "niet zichtbaar";
    }

    @Override
    public void update(List<Object> gegevens) {
        sessie.setVerantwoordelijke((Gebruiker) gegevens.get(0));
        sessie.setTitel((String) gegevens.get(1));
        sessie.setDatumUurSessie((LocalDateTime) gegevens.get(2), (LocalDateTime) gegevens.get(3));
        sessie.setLokaal((Lokaal) gegevens.get(4));
        sessie.setNaamGastspreker((String) gegevens.get(5));
        sessie.setMaximumAantalPlaatsen((Integer) gegevens.get(6));
    }

    @Override
    public void verwijder(boolean verwijder) {
        sessie.setVerwijderd(verwijder);
    }


}
