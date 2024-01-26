package mua;

/**
 * Represents the subject header of a message.
 */
public class SubjectHeader implements Header<String> {
    private String value;

    /**
     * Constructs a SubjectHeader object with the specified subject of the message.
     *
     * @param value
     */
    public SubjectHeader(String value) {
        this.value = value;
    }

    /**
     * Returns the subject of the message.
     *
     * @return the subject of the message.
     */
    @Override
    public String getValue() {
        return value;
    }
}
