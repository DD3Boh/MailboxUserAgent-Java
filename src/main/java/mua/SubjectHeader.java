package mua;

/**
 * Represents the subject header of a message.
 */
public class SubjectHeader implements Header<Subject> {
    private Subject value;

    /**
     * Constructs a SubjectHeader object with the specified subject of the message.
     *
     * @param value
     */
    public SubjectHeader(Subject value) {
        this.value = value;
    }

    /**
     * Returns a string representing the type of the header.
     *
     * @return a string representing the type of the header
     */
    @Override
    public String getType() {
        return "Subject";
    }

    /**
     * Returns the subject of the message.
     *
     * @return the subject of the message.
     */
    @Override
    public Subject getValue() {
        return value;
    }
}
