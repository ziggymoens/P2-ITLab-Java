package userinterface.main;

public class rij {
    private String aj;
    private String start;
    private String eind;
    private String aantal;

    public rij(String aj, String start, String eind, String aantal){
        this.aantal= aantal;
        this.aj = aj;
        this.start = start;
        this.eind = eind;
    }

    public String getAj() {
        return aj;
    }

    public String getStart() {
        return start;
    }

    public String getEind() {
        return eind;
    }

    public String getAantal() {
        return aantal;
    }
}
