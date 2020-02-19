package persistentie.mappersAbs;

import domein.Aankondiging;
import domein.Gebruiker;
import persistentie.PersistentieController;

import java.util.ArrayList;
import java.util.List;

public abstract class AankondigingMapper {
    protected List<Aankondiging> aankondigingList;
    protected PersistentieController persistentieController;

    public AankondigingMapper() {
        this.aankondigingList = new ArrayList<>();
    }

    public abstract List<Aankondiging> getAankodigingen();

    public abstract void voegAankondigingToe(Aankondiging aankondiging);

    public abstract void verwijderAankondiging(Aankondiging aankondiging);

    public abstract void updateAankondiging(Aankondiging aankondiging);

    public abstract void schrijfAankondigingen();

    public abstract void setPersistentieController(PersistentieController persistentieController);

    public abstract void initData();
}
