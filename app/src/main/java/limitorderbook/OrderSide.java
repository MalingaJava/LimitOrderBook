package limitorderbook;

public enum OrderSide {
    // TODO: Implement this properly
    BID ('B'),
    ASK('O');

    public char asChar() {
        return asChar;
    }

    private final char asChar;

    OrderSide(char asChar) {
        this.asChar = asChar;
    }
}
