package domein.enums;

public enum SessieStatus {
    ZICHTBAAR,NIET_ZICHTBAAR, OPEN, GESLOTEN;

    public String toLowerCase(){
        return this.toString().toLowerCase();
    }
}
