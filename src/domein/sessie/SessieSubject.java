package domein.sessie;

import userinterface.main.IObserverSessie;

public interface SessieSubject{
    void add(IObserverSessie observers);
    void remove(IObserverSessie observers);
}
