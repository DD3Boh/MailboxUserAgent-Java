package mua;

import utils.ASCIICharSequence;

/** Represents a Content-Type header. */
public final class ContentTypeHeader implements Header {
  /*
   * Abstraction Function:
   * Represents a Content-Type header with a value.
   * The Content-Type header is used to specify the media type of the content and the charset or boundary.
   * The "variant" value of the header is the media type of the content. The "charset" value of the header
   * is the charset of the content. The "boundary" value of the header is the boundary of the content.
   * The variant value can be:
   * - "text/plain" if the content is plain text
   * - "text/html" if the content is HTML
   * - "multipart/alternative" if the content has more than one representation
   * - "multipart/mixed" if the content has more than one part and one of the parts is an attachment.
   * The charset value is the character encoding of the content.
   * The charset value can be:
   * - "us-ascii" if the content is plain ascii text
   * - "utf-8" if the content is UTF-8 encoded
   * The boundary value is the boundary of the content, used to separate the different parts of the content.
   * The ASCII representation is of the form "Content-Type: value; charset=charset"
   * if charset is not null, "Content-Type: value; boundary=boundary" if boundary is not null.
   * The UI representation is of the form "Part\nvariant".
   *
   * Representation Invariant:
   * - The variant value cannot be null or empty.
   * - The charset value cannot be null or empty.
   * - The boundary value cannot be null or empty.
   * - The variant value must be either "text/plain", "text/html", "multipart/alternative" or "multipart/mixed".
   * - If the variant value is "text/plain" or "text/html", the charset value must be set,
   *   the boundary value must be empty.
   * - The charset value must be either "us-ascii" or "utf-8".
   * - If the variant value is "multipart/alternative" or "multipart/mixed",
   *   the boundary value must be set, the charset value must be empty.
   */
  /** the text value of the Content-Type header */
  private final String variant;

  /** the charset value of the Content-Type header */
  private final String charset;

  /** the boundary value of the Content-Type header */
  private final String boundary;

  /**
   * Constructs a new Content-Type header with the specified text, charset and boundary values. The
   * text value of the Content-Type header must be either "text/plain", "text/html",
   * "multipart/alternative" or "multipart/mixed". If the text value is "text/plain" or "text/html",
   * the value parameter must be the charset value.
   *
   * @param variant the media type of the content
   * @param value the charset or boundary value of the content
   * @throws IllegalArgumentException if the variant or value is null or empty
   * @throws IllegalArgumentException if the variant is not "text/plain", "text/html",
   *     "multipart/alternative" or "multipart/mixed"
   * @throws IllegalArgumentException if the charset value is not "us-ascii" or "utf-8"
   */
  public ContentTypeHeader(String variant, String value) throws IllegalArgumentException {
    if (variant == null || value == null)
      throw new IllegalArgumentException("Content-Type header values cannot be null");

    if (variant.isBlank() || value.isBlank())
      throw new IllegalArgumentException("Content-Type header values cannot be empty");

    if (variant.equals("multipart/alternative") || variant.equals("multipart/mixed")) {
      this.variant = variant;
      this.boundary = value;
      this.charset = "";
    } else if (variant.equals("text/plain") || variant.equals("text/html")) {
      ;
      if (!value.equals("us-ascii") && !value.equals("utf-8"))
        throw new IllegalArgumentException(
            "Content-Type header value must be either us-ascii or utf-8");

      this.variant = variant;
      this.charset = value;
      this.boundary = "";
    } else
      throw new IllegalArgumentException(
          "Content-Type header value must be either text/plain, text/html, multipart/alternative or multipart/mixed");
  }

  /**
   * Static constructor a new Content-Type header from the specified encoded Content-Type String.
   * The encoded Content-Type String is of the form "value; charset=charset" if charset is not null,
   * "value; boundary=boundary" if boundary is not null.
   *
   * @param encodedString the encoded Content-Type String
   * @return the Content-Type header
   * @throws IllegalArgumentException if the value is null or empty.
   * @throws IllegalArgumentException if the value does not contain only two values.
   * @throws IllegalArgumentException if the value does not contain a charset or boundary value
   * @throws IllegalArgumentException if encodedString does not contain a charset or boundary value.
   */
  public static ContentTypeHeader fromEncodedString(String encodedString) {
    if (encodedString == null)
      throw new IllegalArgumentException("Content-Type header value cannot be null");
    if (encodedString.isBlank())
      throw new IllegalArgumentException("Content-Type header value cannot be empty");

    String[] split = encodedString.split(";");
    if (split.length != 2)
      throw new IllegalArgumentException("Content-Type header value must contain only two values");

    String variant = split[0].trim();
    String value = split[1].trim();

    if (value.startsWith("boundary=")) value = value.replace("boundary=", "");
    else if (value.startsWith("charset=")) value = value.replace("charset=", "").replace("\"", "");
    else
      throw new IllegalArgumentException(
          "Content-Type header value must contain a charset or boundary value");

    return new ContentTypeHeader(variant, value);
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
   * @return the value of the Content-Type header, the variant value.
   */
  @Override
  public String getValue() {
    return variant;
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
    if (!charset.isBlank()) valueString = variant + "; charset=\"" + charset + "\"";
    else if (!boundary.isBlank()) valueString = variant + "; boundary=" + boundary;
    else valueString = variant;

    return ASCIICharSequence.of(getType() + ": " + valueString);
  }

  /**
   * Returns the boundary value of the Content-Type header.
   *
   * @return the boundary value.
   */
  public String getBoundary() {
    return boundary;
  }

  /**
   * Encodes the UI representation of the Content Type Header's name, in a string format. The UI
   * representation is the representation of the header's name that needs to be displayed to the
   * user when creating cards or tables. The String generated is of the form "Part\nvariant".
   *
   * @param isExtended whether the current UI representation is the extended version
   * @return the UI representation of the Content-Type Header
   */
  @Override
  public String encodeUIName(boolean isExtended) {
    if (!isExtended) return "";
    return "Part\n" + variant;
  }
}
