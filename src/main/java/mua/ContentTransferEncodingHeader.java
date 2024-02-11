package mua;

import utils.ASCIICharSequence;

/** Represents a Content-Transfer-Encoding header. */
public class ContentTransferEncodingHeader implements Header<String> {
  /** the value of the Content-Transfer-Encoding header */
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

  @Override
  public ASCIICharSequence encodeToASCII() {
    return ASCIICharSequence.of(getType() + ": " + getValue());
  }

  /**
   * Returns a string representation of the Content-Transfer-Encoding header.
   *
   * @return a string representation of the Content-Transfer-Encoding header
   */
  @Override
  public String toString() {
    return value;
  }
}
