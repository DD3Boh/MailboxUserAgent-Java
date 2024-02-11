package mua;

import utils.ASCIICharSequence;

/** Represents a Content-Type header. */
public final class ContentTypeHeader implements Header<String> {
  /** the text value of the Content-Type header */
  private final String value;

  /** the charset value of the Content-Type header */
  private final String charset;

  /** the boundary value of the Content-Type header */
  private final String boundary;

  /**
   * Constructs a new Content-Type header parsing the specified content type. The string that needs
   * parsing is of the form "value; boundary=frontier".
   *
   * @param value the content type string, needs parsing.
   */
  public ContentTypeHeader(String value) {
    if (value.contains("boundary")) {
      String[] split = value.split(";");
      this.value = split[0].trim();
      this.boundary = "frontier";
      this.charset = null;
    } else if (value.contains("charset")) {
      String[] split = value.split(";");
      this.value = split[0].trim();
      this.charset = split[1].trim().replace("charset=", "").replace("\"", "");
      this.boundary = null;
    } else {
      this.value = value;
      this.charset = null;
      this.boundary = null;
    }
  }

  /**
   * Constructs a new Content-Type header with the specified text, charset and boundary values.
   *
   * @param textValue the text value of the header
   * @param charset the charset value of the header
   * @param boundary the boundary value of the header
   */
  public ContentTypeHeader(String textValue, String charset, String boundary) {
    this.value = textValue;
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
    return value;
  }

  /**
   * Returns the ASCII representation of the Content-Type header. The representation is of the form
   * "Content-Type: value; charset=charset" if charset is not null, "Content-Type: value;
   * boundary=boundary" if boundary is not null.
   *
   * @return the ASCII representation of the Content-Type header
   */
  @Override
  public ASCIICharSequence encodeToASCII() {
    String valueString = "";
    if (charset != null) valueString = value + "; charset=\"" + charset + "\"";
    else if (boundary != null) valueString = value + "; boundary=" + boundary;
    else valueString = value;

    return ASCIICharSequence.of(getType() + ": " + valueString);
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
   * Encodes the UI representation of the Content Type Header's name, in a string format.
   * The UI representation is the representation of the header's name that needs to be displayed to the user
   * when creating cards or tables.
   * The String generated is of the form "Part\nvalue".
   *
   * @return the UI representation of the Content-Type Header
   */
  @Override
  public String encodeUIName() {
    return "Part\n" + value;
  }
}
