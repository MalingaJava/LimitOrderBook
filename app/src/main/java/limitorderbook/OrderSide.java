package limitorderbook;

/**
 * Order side constants.
 *
 * @author Malinga.
 */
public enum OrderSide {
    // TODO: Implement this properly
    BID('B'),
    ASK('O');

    private final char asChar;

    OrderSide(char asChar) {
        this.asChar = asChar;
    }

    public char asChar() {
        return asChar;
    }
}
