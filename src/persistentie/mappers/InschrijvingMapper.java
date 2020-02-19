package persistentie.mappers;

import domein.Inschrijving;
import persistentie.PersistentieController;

import java.util.ArrayList;
import java.util.List;

public abstract class InschrijvingMapper {
    protected List<Inschrijving> inschrijvingList;
    protected PersistentieController persistentieController;

    public InschrijvingMapper() {
        this.inschrijvingList = new ArrayList<>();
    }

    public abstract List<Inschrijving> getAankodigingen();

    public abstract void voegInschrijvingToe(Inschrijving inschrijving);

    public abstract void verwijderInschrijving(Inschrijving inschrijving);

    public abstract void updateInschrijving(Inschrijving inschrijving);

    public abstract void schrijfInschrijvingen();

    public abstract void setPersistentieController(PersistentieController persistentieController);

    public abstract void initData();
}
