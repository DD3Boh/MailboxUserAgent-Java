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
            this.boundary = "frontier";
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
     * Constructs a new Content-Type header with the specified text, charset and boundary values.
     *
     * @param textValue the text value of the header
     * @param charset   the charset value of the header
     * @param boundary  the boundary value of the header
     */
    public ContentTypeHeader(String textValue, String charset, String boundary) {
        this.textValue = textValue;
        this.charset = charset;
        this.boundary = boundary;
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
        return textValue;
    }

    /**
     * Returns the boundary value of the Content-Type header.
     *
     * @return the boundary value
     */
    public String getBoundary() {
        return boundary;
    }

    /**
     * Returns a string representation of the Content-Type header.
     *
     * @return a string representation of the Content-Type header
     */
    @Override
    public String toString() {
        String value = "";
        if (charset != null)
            value = textValue + "; charset=\"" + charset + "\"";
        else if (boundary != null)
            value = textValue + "; boundary=" + boundary;
        else
            value = textValue;

        return getType() + ": " + value;
    }
}
