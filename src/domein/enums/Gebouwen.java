package domein.enums;

public enum Gebouwen {
    B_A(Campus.AALST, "B"), B_G(Campus.SCHOONMEERSEN, "B"),C(Campus.SCHOONMEERSEN, "C"),D(Campus.SCHOONMEERSEN, "D"),P(Campus.SCHOONMEERSEN, "P");

    final Campus campus;
    final String displayValue;

    Gebouwen(Campus campus, String display) {
        this.campus = campus;
        this.displayValue = display;
    }
    public Campus getCampus() {
        return campus;
    }
    public String getDisplayValue() {
        return displayValue;
    }
}
