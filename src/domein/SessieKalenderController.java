package domein;

public class SessieKalenderController {
    final SessieKalenderBeheerder sessieKalenderBeheerder = new SessieKalenderBeheerder();

    public void close(){
        sessieKalenderBeheerder.closePersistentie();
    }
}
