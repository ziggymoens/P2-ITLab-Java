package domein.sessie;

import domein.Lokaal;
import domein.gebruiker.Gebruiker;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@DiscriminatorValue("NIET_ZICHTBAAR")
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
        if (gegevens.get(0) != null) {
            sessie.setVerantwoordelijke((Gebruiker) gegevens.get(0));
        }
        if (gegevens.get(1) != null && !((String) gegevens.get(1)).isBlank()) {
            sessie.setTitel((String) gegevens.get(1));
        }
        if (gegevens.get(2) != null) {
            sessie.setStartSessie((LocalDateTime) gegevens.get(2));
        }
        if (gegevens.get(3) != null) {
            sessie.setEindeSessie((LocalDateTime) gegevens.get(3));
        }
        if (gegevens.get(4) != null) {
            sessie.setLokaal((Lokaal) gegevens.get(4));
        }
        if (gegevens.get(5) != null && !((String) gegevens.get(5)).isBlank()) {
            sessie.setNaamGastspreker((String) gegevens.get(5));
        }
        if (gegevens.get(6) != null) {
            sessie.setMaximumAantalPlaatsen((Integer) gegevens.get(6));
        }
        //controleData();
    }

    @Override
    public void verwijder(boolean verwijder) {
        sessie.setVerwijderd(verwijder);
    }


}
