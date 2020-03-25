package domein.sessie;

import domein.Lokaal;
import domein.gebruiker.Gebruiker;
import exceptions.domein.SessieException;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@DiscriminatorValue("zichtbaar")
public class ZichtbaarState extends SessieState {

    private static final long serialVersionUID = 5423012923739736619L;

    public ZichtbaarState() {
    }

    public ZichtbaarState(Sessie sessie) {
        super(sessie);
    }

    @Override
    public String getStatus() {
        return "zichtbaar";
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
        throw new SessieException("State;Deze sessie kan niet worden verwijderd aangezien deze zichtbaar is.");
    }
}
