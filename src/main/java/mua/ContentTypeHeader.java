package mua;

/**
 * Represents a Content-Type header.
 */
public class ContentTypeHeader implements Header<String> {
    private final String textValue;
    private final String charset;
    private final String boundary;

    /**
     * Constructs a new Content-Type header parsing the specified content type.
     * The string that needs parsing is of the form "<value>; boundary=frontier".
     *
     * @param value the content type string, needs parsing.
     */
    public ContentTypeHeader(String value) {
        if (value.contains("boundary")) {
            String[] split = value.split(";");
            this.textValue = split[0].trim();
            this.boundary = "boundary=frontier";
            this.charset = null;
        } else if (value.contains("charset")) {
            String[] split = value.split(";");
            this.textValue = split[0].trim();
            this.charset = split[1].trim().replace("charset=", "").replace("\"", "");
            this.boundary = null;
        } else {
            this.textValue = value;
            this.charset = null;
            this.boundary = null;
        }
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
        if (charset != null)
            return textValue + "; charset=\"" + charset + "\"";
        else if (boundary != null)
            return textValue + "; " + boundary;
        else
            return textValue;
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
