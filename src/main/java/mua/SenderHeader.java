package mua;

/**
 * Represents the sender header of a message.
 */
public class SenderHeader implements Header<String> {
    private String value;

    /**
     * Constructs a SenderHeader object with the specified email address of the sender.
     *
     * @param value
     */
    public SenderHeader(String value) {
        this.value = value;
    }

    /**
     * Returns the email address of the sender.
     *
     * @return the email address of the sender.
     */
    @Override
    public String getValue() {
        return value;
    }
}
