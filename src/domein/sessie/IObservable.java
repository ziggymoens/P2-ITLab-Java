package domein.sessie;

import userinterface.main.IObserver;

public interface IObservable {
    void verwittig();
    void addObserver(IObserver iObserver);
    void removeObserver(IObserver iObserver);
}
