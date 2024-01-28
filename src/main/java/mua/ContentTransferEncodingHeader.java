package mua;

/**
 * Represents a Content-Transfer-Encoding header.
 */
public class ContentTransferEncodingHeader implements Header<String> {
    private final String value;

    /**
     * Constructs a new Content-Transfer-Encoding header with the specified encoding.
     *
     * @param value the encoding type
     */
    public ContentTransferEncodingHeader(String value) {
        this.value = value;
    }

    /**
     * Returns the type of the header.
     *
     * @return the type of the header, "Content-Transfer-Encoding"
     */
    @Override
    public String getType() {
        return "Content-Transfer-Encoding";
    }

    /**
     * Returns the value of the Content-Transfer-Encoding header.
     *
     * @return the encoding type value
     */
    @Override
    public String getValue() {
        return value;
    }

    /**
     * Returns a string representation of the Content-Transfer-Encoding header.
     *
     * @return a string representation of the Content-Transfer-Encoding header
     */
    @Override
    public String toString() {
        return getType() + ": " + getValue();
    }
}
