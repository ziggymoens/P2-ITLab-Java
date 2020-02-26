package userinterface.kalender;

import java.util.Arrays;
import java.util.List;

public class Maand {
    private int[] startdag2020 = {2, 5, 6, 2, 4, 0, 2, 5, 1, 3, 6, 1};
    private int[] aantalDagen2020 = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private String[] afkortingMaand = {"jan", "feb", "maa", "apr", "jun", "jul", "aug", "sep", "okt", "nov", "dec"};
    private String[] ms = {"januari", "februari", "maart", "april", "mei", "juni", "juli", "augustus", "september", "oktober", "november", "december"};
    private List<String> maanden = Arrays.asList(ms);
    private int maand;

    public Maand(int maand) {
        this.maand = maand;
    }

    public int aantalDagen(){
        return aantalDagen2020[maand -1];
    }

    public int startDag(){
        return startdag2020[maand-1];
    }

    public String afkorting(){
        return maanden.get(maand-1);
    }

    public int getNummer() {
        return maand;
    }
}
