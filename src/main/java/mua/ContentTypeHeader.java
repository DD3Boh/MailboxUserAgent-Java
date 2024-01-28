package mua;

/**
 * Represents a Content-Type header.
 */
public class ContentTypeHeader implements Header<String> {
    private final String value;

    /**
     * Constructs a new Content-Type header with the specified media type.
     *
     * @param value the media type
     */
    public ContentTypeHeader(String value) {
        this.value = value;
    }

    /**
     * Returns the type of the header.
     *
     * @return the type of the header, "Content-Type"
     */
    @Override
    public String getType() {
        return "Content-Type";
    }

    /**
     * Returns the value of the Content-Type header.
     *
     * @return the media type value
     */
    @Override
    public String getValue() {
        return value;
    }

    /**
     * Returns a string representation of the Content-Type header.
     *
     * @return a string representation of the Content-Type header
     */
    @Override
    public String toString() {
        return getType() + ": " + getValue();
    }
}
