package domein.interfacesDomein;

import domein.enums.HerinneringTijdstippen;

public interface IHerinnering {
    int getDagenVoorafInt();

    HerinneringTijdstippen getDagenVooraf();

    String getHerinneringsId();
}
