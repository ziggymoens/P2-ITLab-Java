package domein.enums;

public enum Campussen {
    SCHOONMEERSEN("SCH"),
    AALST("AAR");

    private final String afkorting;

    Campussen(final String afk) {
        this.afkorting = afk;
    }

    public String getAfkorting() {
        return afkorting;
    }
}
