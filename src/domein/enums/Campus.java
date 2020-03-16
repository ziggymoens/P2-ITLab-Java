package domein.enums;

public enum Campus {
    SCHOONMEERSEN(Stad.GENT, "SCH"),
    AALST(Stad.AALST, "AAR");

    final Stad stad;
    final String code;

    Campus(Stad stad, String code) {
        this.stad = stad;
        this.code = code;
    }

    public Stad getStad() {
        return stad;
    }

    public String getCode() {
        return code;
    }
}
