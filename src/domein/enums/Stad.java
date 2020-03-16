package domein.enums;

public enum Stad {
    GENT(9000), AALST(9300);

    final int postcode;
    Stad(int postcode) {
        this.postcode = postcode;
    }
    public int getPostcode() {
        return postcode;
    }
}
