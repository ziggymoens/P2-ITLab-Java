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

    public void geefAllesVan(String type){
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

    public void geefTopSessieInschrijvingen(int aantal){
        try {
            Connection conn = DriverManager.getConnection(connectionUrl);
            Statement stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery(String.format("SELECT TOP %d sessieId, count(inschrijvingsId) as 'inschrijvingen' from inschrijving i right join sessie s on i.sessie_sessieId = s.sessieId GROUP by sessieId order by 2 desc, 1", aantal));
            boolean append = true;
            CsvDriver.writeToCsv(results, System.out, append);
        } catch (Exception e) {
            throw new StatistiekException("geef Top Sessie Aanwezigheden");
        }
    }

    public void geefTopSessieAanwezigheden(int aantal){
        try {
            Connection conn = DriverManager.getConnection(connectionUrl);
            Statement stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery(String.format("SELECT TOP %d sessieId, count(inschrijvingsId) as 'Aanwezigheden bij sessie' from sessiestatus ss join sessie s on ss.sessie_sessieId = s.sessieId join inschrijving i on s.sessieId = i.sessie_sessieId where ss.sessieStatus = 'gesloten' and i.statusAanwezigheid = 1 group by s.sessieId;", aantal));
            boolean append = true;
            CsvDriver.writeToCsv(results, System.out, append);
        } catch (Exception e) {
            throw new StatistiekException("geef Top Sessie Aanwezigheden");
        }
    }

}
