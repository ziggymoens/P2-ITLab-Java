package domein;

import domein.interfacesDomein.IStatistiek;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.sql.*;

import exceptions.domein.StatistiekException;
import org.relique.jdbc.csv.CsvDriver;

public class Statistiek implements IStatistiek {

    private final String IP_SERVER="52.233.199.3";
    private final String DB_NAME="ITLab_JAVA";
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

    @Override
    public String geefAllesVan(String type) {
        try {
            Connection conn = DriverManager.getConnection(connectionUrl);
            Statement stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery(String.format("SELECT * from %s", type));
            boolean append = true;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            String utf8 = StandardCharsets.UTF_8.name();
            PrintStream ps = new PrintStream(baos, true, utf8);
            CsvDriver.writeToCsv(results, ps, append);
            return baos.toString(utf8);
        } catch (Exception e) {
            throw new StatistiekException("Geef alles van " + type);
        }
    }

    //region Sessie
    @Override
    public String geefTopSessieTabel(String tabel, int aantal){
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
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            String utf8 = StandardCharsets.UTF_8.name();
            PrintStream ps = new PrintStream(baos, true, utf8);
            CsvDriver.writeToCsv(results, ps, append);
            return baos.toString(utf8);
        } catch (Exception e) {
            throw new StatistiekException("geef Top Sessie Tabel, " + naam);
        }
    }
    //endregion

    //region Gebruiker
    @Override
    public String geefTopGebruikerTabel(String tabel, int aantal){
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
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            String utf8 = StandardCharsets.UTF_8.name();
            PrintStream ps = new PrintStream(baos, true, utf8);
            CsvDriver.writeToCsv(results, ps, append);
            return baos.toString(utf8);
        } catch (Exception e) {
            throw new StatistiekException("geef Top Gebruiker Tabel, " + naam);
        }
    }

    public void overzichtTopGebruikers(){
        overzichtTopGebruikers("");
    }

    @Override
    public String overzichtTopGebruikers(String type){
        try {
            Connection conn = DriverManager.getConnection(connectionUrl);
            Statement stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery(String.format("SELECT gebruikersnaam, naam, gp.gebruikerProfiel, count(distinct a.aankondigingsId) as 'Aantal aankondigingen', count(distinct feedbackId) as 'Aantal feedback', count(distinct herinneringsId) as 'Aantal herinneringen', count(distinct inschrijvingsId) as 'Aantal inschrijvingen', COUNT(distinct mediaId) as 'Aantal media' from gebruiker g full join aankondiging a on a.gebruiker_gebruikersnaam = g.gebruikersnaam full join herinnering h on h.herinneringsId = a.herinnering_herinneringsId full join feedback f on f.gebruiker_gebruikersnaam = g.gebruikersnaam full join inschrijving i on i.gebruiker_gebruikersnaam = g.gebruikersnaam full join media m on m.gebruiker_gebruikersnaam = g.gebruikersnaam join gebruikersprofiel gp on g.gebruikersnaam = gp.gebruiker_gebruikersnaam %s GROUP by g.gebruikersnaam, g.naam, gp.gebruikerProfiel order by 3, 2, 4 desc, 5 desc, 6 DESC, 7 desc, 8 DESC;", type.isBlank()?"": String.format("where gp.gebruikerProfiel = '%s'", type)));
            boolean append = true;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            String utf8 = StandardCharsets.UTF_8.name();
            PrintStream ps = new PrintStream(baos, true, utf8);
            CsvDriver.writeToCsv(results, ps, append);
            return baos.toString(utf8);
        } catch (Exception e) {
            throw new StatistiekException("geef Top Gebruiker");
        }
    }
    //endregion

    //region Lokaal
    @Override
    public String overzichtLokalen(int aantal){
        try {
            Connection conn = DriverManager.getConnection(connectionUrl);
            Statement stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery(String.format("select top %d l.lokaalCode, case when l.gebouw = 'B_A' then 'Aalst - B' when l.gebouw = 'B_G' then 'Schoonmeersen - B' when l.gebouw = 'C' then 'Schoonmeersen - C' when l.gebouw = 'D' then 'Schoonmeersen - D' when l.gebouw = 'P' then 'Schoonmeersen - P' end as 'Campus - gebouw', count(s.sessieId) as 'Aantal sessies' from lokaal l join sessie s on s.lokaal_lokaalCode = l.lokaalCode group by lokaalCode, gebouw order by 3 desc, 2, 1;", aantal));
            boolean append = true;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            String utf8 = StandardCharsets.UTF_8.name();
            PrintStream ps = new PrintStream(baos, true, utf8);
            CsvDriver.writeToCsv(results, ps, append);
            return baos.toString(utf8);
        } catch (Exception e) {
            throw new StatistiekException("geef Top Gebruiker");
        }
    }


    //endregion


}



