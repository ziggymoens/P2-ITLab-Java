package domein.enums;

import java.util.Arrays;

public enum Gebouwen {
    SCGB("SCHOONMEERSEN"),
    SCHC("SCHOONMEERSEN"),
    SCHD("SCHOONMEERSEN"),
    SCHP("SCHOONMEERSEN"),
    AARB("AALST");

    private final Campussen campus;

    Gebouwen(final String campus) {
        this.campus = Arrays.stream(Campussen.values()).filter(c -> c.toString().equals(campus)).findFirst().orElse(null);
    }

    public Campussen getCampus() {
        return campus;
    }
}
