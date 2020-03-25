package domein.interfacesDomein;

public interface IStatistiek {
    String geefAllesVan(String type);

    //region Sessie
    String geefTopSessieTabel(String tabel, int aantal);

    //region Gebruiker
    String geefTopGebruikerTabel(String tabel, int aantal);

    String overzichtTopGebruikers(String type);

    //region Lokaal
    String overzichtLokalen(int aantal);
}
