package domein.interfacesDomein;

import domein.enums.HerinneringTijdstip;

public interface IHerinnering {
    int getDagenVoorafInt();

    HerinneringTijdstip getDagenVooraf();

    String getHerinneringsId();
}
