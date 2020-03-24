package domein;

import domein.interfacesDomein.IStatistiek;

import java.sql.*;

import exceptions.domein.StatistiekException;
import org.relique.jdbc.csv.CsvDriver;

public class Statistiek implements IStatistiek {

    private final String IP_SERVER="52.233.199.3";
    private final String DB_NAME="ITLab";
    private final String DB_USER="ziggy.moens";
    private final String DB_PWD="C0165.974d77";
    private String connectionUrl ="jdbc:sqlserver://" + IP_SERVER + ":1433;"
            + "database=" + DB_NAME + ";"
            + "user=" + DB_USER + ";"
            + "password=" + DB_PWD + ";"
            + "encrypt=false;"
            + "trustServerCertificate=false;"
            + "loginTimeout=30;";

    public Statistiek(){
    }

    public void geefAllesVan(String type) {
        try {
            Connection conn = DriverManager.getConnection(connectionUrl);
            Statement stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery(String.format("SELECT * from %s", type));
            boolean append = true;
            CsvDriver.writeToCsv(results, System.out, append);
        } catch (Exception e) {
            throw new StatistiekException("Geef alles van " + type);
        }
    }

    //region Sessie
    public void geefTopSessieTabel(String tabel, int aantal){
        String naam = "";
        try {
            naam = tabel;
            boolean aanwezigheid = false;
            if (tabel.equals("aanwezigheid")){
                aanwezigheid = true;
                tabel = "inschrijving";
            }
            Connection conn = DriverManager.getConnection(connectionUrl);
            Statement stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery(String.format("SELECT TOP %d sessieId, s.titel, count(%s) as '%s voor de sessie' from %s t join sessie s on t.sessie_sessieId = s.sessieId join sessiestatus ss on ss.sessie_sessieId = s.sessieId %sGROUP by s.sessieId, s.titel order by 3 desc, 1;",aantal,tabel.endsWith("g")?String.format("%ssId",tabel): String.format("%sId", tabel),naam, tabel, aanwezigheid?" where ss.sessieStatus = 'gesloten' and t.statusAanwezigheid = 1 ":""));
            boolean append = true;
            CsvDriver.writeToCsv(results, System.out, append);
        } catch (Exception e) {
            e.printStackTrace();
            throw new StatistiekException("geef Top Sessie Tabel, " + naam);
        }
    }
    //endregion

    //region Gebruiker
    public void geefTopGebruikerTabel(String tabel, int aantal){
        String naam = "";
        try {
            naam = tabel;
            boolean aanwezigheid = false;
            if (tabel.equals("aanwezigheid")){
                aanwezigheid = true;
                tabel = "inschrijving";
            }
            Connection conn = DriverManager.getConnection(connectionUrl);
            Statement stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery(String.format("SELECT TOP %d g.gebruikersnaam,g.naam, count(%s) as '%s per gebruiker' from gebruiker g join gebruikersprofiel gp on g.gebruikersnaam = gp.gebruiker_gebruikersnaam join gebruikersstatus gs on gs.gebruiker_gebruikersnaam = g.gebruikersnaam join %s t on t.%s_gebruikersnaam = g.gebruikersnaam %s where %s gs.gebruikerStatus != 'geblokkeerd' %s group by g.gebruikersnaam, g.naam order by 3 desc, 1;", aantal, tabel.endsWith("g")?String.format("%ssId",tabel): String.format("%sId", tabel),naam, tabel,tabel.equals("sessie")?"verantwoordelijke":"gebruiker", aanwezigheid?"join sessiestatus ss on ss.sessie_sessieId = t.sessie_sessieId":"", tabel.equals("sessie")||tabel.equals("aankondiging")||tabel.equals("media")?"":"gp.gebruikerProfiel = 'gebruiker' and",aanwezigheid?" and ss.sessieStatus = 'gesloten' and t.statusAanwezigheid = 1 and ss.sessiestatus = 'gesloten'":""));
            boolean append = true;
            CsvDriver.writeToCsv(results, System.out, append);
        } catch (Exception e) {
            e.printStackTrace();
            throw new StatistiekException("geef Top Gebruiker Tabel, " + naam);
        }
    }
    //endregion



}



