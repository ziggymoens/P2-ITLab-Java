package persistentie.mappers;

import domein.Herinnering;
import persistentie.PersistentieController;

import java.util.ArrayList;
import java.util.List;

public abstract class HerinneringMapper {
    protected List<Herinnering> herinneringList;
    protected PersistentieController persistentieController;

    public HerinneringMapper() {
        this.herinneringList = new ArrayList<>();
    }

    public abstract List<Herinnering> getHerinneringen();

    public abstract void voegHerinneringToe(Herinnering herinnering);

    public abstract void verwijderHerinnering(Herinnering herinnering);

    public abstract void updateHerinnering(Herinnering herinnering);

    public abstract void schrijfHerinneringen();

    public abstract void setPersistentieController(PersistentieController persistentieController);

    public abstract void initData();
}
