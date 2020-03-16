package domein.enums;

public enum HerinneringTijdstip {
    NUL(0), EEN(1), TWEE(2), DRIE(3), WEEK(7);

    private final int dagen;

    HerinneringTijdstip(final int dagen) {
        this.dagen = dagen;
    }

    public int getDagen() {
        return dagen;
    }
}
