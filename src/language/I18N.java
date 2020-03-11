package language;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;

import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.Callable;


public final class I18N {

    private static final ObjectProperty<Locale> locale;

    static {
        locale = new SimpleObjectProperty<>(getDefaultLocale());
        locale.addListener((o, oldV, newV) -> Locale.setDefault(newV));
    }

    //region Locale
    public static List<Locale> getSupportedLangs() {
        return new ArrayList<>(Arrays.asList(new Locale("be_nl"), Locale.US, Locale.FRANCE));
    }

    public static Locale getDefaultLocale() {
        Locale def = Locale.getDefault();
        return getSupportedLangs().contains(def) ? def : new Locale("be_nl");
    }

    public static Locale getLocale() {
        return locale.get();
    }

    public static void setLocale(Locale locale) {
        localeProperty().set(locale);
        Locale.setDefault(locale);
    }

    public static ObjectProperty<Locale> localeProperty() {
        return locale;
    }
    //endregion

    public static String get(final String key, final Object... args) {
        ResourceBundle bundle = ResourceBundle.getBundle("language/resourcebundle/i18n", getLocale());
        return MessageFormat.format(bundle.getString(key), args);
    }

    public static StringBinding createStringBinding(final String key, Object... args) {
        return Bindings.createStringBinding(() -> get(key, args), locale);
    }

    public static StringBinding createStringBinding(Callable<String> func) {
        return Bindings.createStringBinding(func, locale);
    }

    public static Label labelForKey(Callable<String> func) {
        Label label = new Label();
        label.textProperty().bind(createStringBinding(func));
        return label;
    }

    public static Button buttonForKey(final String key, final Object... args) {
        Button button = new Button();
        button.textProperty().bind(createStringBinding(key, args));
        return button;
    }

    public static Tooltip tooltipForKey(final String key, final Object... args) {
        Tooltip tooltip = new Tooltip();
        tooltip.textProperty().bind(createStringBinding(key, args));
        return tooltip;
    }
}
