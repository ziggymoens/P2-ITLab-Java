package userinterface.kalender;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KalenderGegevens {
    public static final int BEGIN = 2017;
    public static final int EIND = 2023;
    //eerste dag van de maand, maandag = 0, dinsdag = 1, ... zondag = 6
    //Bij aanvullen BEGIN en/of EIND uitbreiden
    private int[] startdag2017 = {6, 2, 2, 5, 0, 3, 5, 1, 4, 6, 2, 4};
    private int[] startdag2018 = {0, 3, 3, 6, 1, 4, 6, 2, 5, 0, 3, 5};
    private int[] startdag2019 = {1, 4, 4, 0, 2, 5, 0, 3, 6, 1, 4, 6};
    private int[] startdag2020 = {2, 5, 6, 2, 4, 0, 2, 5, 1, 3, 6, 1};
    private int[] startdag2021 = {4, 0, 0, 3, 5, 1, 3, 6, 2, 4, 0, 2};
    private int[] startdag2022 = {5, 1, 1, 4, 6, 2, 4, 0, 3, 5, 1, 3};
    private int[] startdag2023 = {6, 2, 2, 5, 0, 3, 5, 1, 4, 6, 2, 4};

    private Map<Integer, int[]> startdagen;

    private int[] aantalDagen366 = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private int[] aantalDagen365 = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private String[] afkortingMaand = {"jan", "feb", "maa", "apr", "mei","jun", "jul", "aug", "sep", "okt", "nov", "dec"};
    private String[] ms = {"januari", "februari", "maart", "april", "mei", "juni", "juli", "augustus", "september", "oktober", "november", "december"};
    private List<String> naam = Arrays.asList(ms);

    private int maand;
    private int jaar;

    public KalenderGegevens(int maand, int jaar) {
        startdagen = new HashMap<>();
        startdagen.put(2017, startdag2017);
        startdagen.put(2018, startdag2018);
        startdagen.put(2019, startdag2019);
        startdagen.put(2020, startdag2020);
        startdagen.put(2021, startdag2021);
        startdagen.put(2022, startdag2022);
        startdagen.put(2023, startdag2023);

        this.maand = maand;
        this.jaar = jaar;
    }

    public int aantalDagen(){
        if (jaar % 4 == 0) {
            return aantalDagen366[maand - 1];
        }else{
            return aantalDagen365[maand - 1];
        }
    }

    public int startDag(){
        return startdagen.get(jaar)[maand-1];
    }

    public String geefNaamMaand(){
        return naam.get(maand-1);
    }

    public String afkorting(){
        return afkortingMaand[maand-1];
    }

    public int geefNummerMaand() {
        return maand;
    }

    public KalenderGegevens volgendeMaand(){
        if (maand + 1 == 13){
            return new KalenderGegevens(1, jaar+1);
        }
        return new KalenderGegevens(maand + 1, jaar);
    }

    public KalenderGegevens vorigeMaand(){
        if (maand - 1 == 0){
            return new KalenderGegevens(12, jaar-1);
        }
        return new KalenderGegevens(maand - 1, jaar);
    }

    public KalenderGegevens volgendJaar(){
        return new KalenderGegevens(maand, jaar+1);
    }

    public KalenderGegevens vorigJaar(){
        return new KalenderGegevens(maand, jaar-1);
    }

    public int geefJaar() {
        return jaar;
    }
}
