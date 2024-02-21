package mua;

import utils.ASCIICharSequence;

/** Represents a Content-Transfer-Encoding header. */
public final class ContentTransferEncodingHeader implements Header {
  /*
   * Abstraction Function:
   * Represents a Content-Transfer-Encoding header with a value.
   * The Content-Transfer-Encoding header is used to specify the encoding type of the content.
   *
   * Representation Invariant:
   * - The value cannot be null or empty.
   */
  /** the value of the Content-Transfer-Encoding header */
  private final String value;

  /**
   * Constructs a new Content-Transfer-Encoding header with the specified encoding.
   *
   * @param value the encoding type
   * @throws IllegalArgumentException if the value is null or empty
   */
  public ContentTransferEncodingHeader(String value) throws IllegalArgumentException {
    if (value == null || value.isEmpty())
      throw new IllegalArgumentException("The value cannot be null or empty");

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
   * Returns an ASCII representation of the Content-Transfer-Encoding header. The ASCII
   * representation is of the form "Content-Transfer-Encoding: value".
   *
   * @return an ASCII representation of the Content-Transfer-Encoding header
   */
  @Override
  public ASCIICharSequence encodeToASCII() {
    return ASCIICharSequence.of(getType() + ": " + getValue());
  }
}
