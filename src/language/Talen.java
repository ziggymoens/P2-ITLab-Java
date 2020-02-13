package language;

import java.util.Locale;
import java.util.ResourceBundle;

public class Talen {
    private static Locale locale;
    private static ResourceBundle bundle;
    private static final String location = "language/resourcebundle/i18n";

    static {
        setLocale(new Locale("en"));
        setBundle();
    }

    /**
     * Contructor voor LanguageResource zonder parameters, Engels wordt
     * ingesteld als taal
     */
    public Talen() {
        setLocale(new Locale("be_nl"));
        setBundle();
    }

    /**
     * Constructor voor LanguageResource adhv meegegeven taal
     *
     * @param locale gewenste taal
     */
    public Talen(Locale locale) {
        setLocale(locale);
    }

    /**
     * Locale van de LanguageResource aanpassen
     *
     * @param locale nieuwe gewenste taal
     */
    public static void setLocale(Locale locale) {
        if (locale.toString().equals("us_en") || locale.toString().equals("fr_fr") || locale.toString().equals("be_nl")) {
            Talen.locale = locale;
        } else {
            Talen.locale = new Locale("be_nl");
        }
        setBundle();
    }

    /**
     * Bundle in LanguageResource initialiseren met de gegeven taal
     */
    private static void setBundle() {

        Talen.bundle = ResourceBundle.getBundle(location, getLocale());
    }

    /**
     * Huidige taal van de LanguageResource opvragen
     *
     * @return Locale van de huidige taal
     */

    public static Locale getLocale() {
        return Talen.locale;
    }

    /**
     * De huidige bundle opvragen
     *
     * @return Bundle met huidige taal
     */
    public ResourceBundle getBundle() {

        return bundle;
    }

    /**
     * Woord opvragen in een bepaalde taal dat niet de standaard taal is
     *
     * @param string Key van het opgevraagde woord
     * @param locale Gewenste taal
     * @return String met het woord in de gekozen taal
     */
    public static String getStringLanguage(String string, Locale locale) {
        return ResourceBundle.getBundle(location, locale).getString(string);
    }

    /**
     * Woord opvragen in de huidige taal
     *
     * @param string Key van het opgevraagde woord
     * @return String met het woord in de huidige taal
     */
    public static String getString(String string) {
        return bundle.getString(string);
    }
}
