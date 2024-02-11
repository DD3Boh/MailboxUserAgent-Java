package mua;

import utils.ASCIICharSequence;

/** Represents a Content-Disposition header. Implements the Header interface with a String value. */
public class ContentDispositionHeader implements Header<String> {
  /** the value of the Content-Disposition header */
  private final String value;

  /**
   * Constructs a ContentDispositionHeader object with the given value. Parses the value to extract
   * the disposition type and filename.
   *
   * @param value the value of the Content-Disposition header
   */
  public ContentDispositionHeader(String value) {
    int filenameIndex = value.indexOf("filename=");

    if (filenameIndex == -1) {
      if (value.isEmpty())
        throw new IllegalArgumentException("Content-Disposition header value cannot be empty");
      else
        this.value = value;
    } else
      this.value = value.substring(filenameIndex + 9).replace("\"", "");
  }

  /**
   * Returns the type of the Content-Disposition header.
   *
   * @return the type of the Content-Disposition header
   */
  @Override
  public String getType() {
    return "Content-Disposition";
  }

  /**
   * Returns the value of the Content-Disposition header.
   *
   * @return the value of the Content-Disposition header
   */
  @Override
  public String getValue() {
    return value;
  }

  /**
   * Encodes the Content-Disposition header to ASCII representation.
   *
   * @return the ASCII representation of the Content-Disposition header
   */
  @Override
  public ASCIICharSequence encodeToASCII() {
    return ASCIICharSequence.of(
        getType() + ": attachment; filename=\"" + value + "\"");
  }

  /**
   * Returns the string representation of the Content-Disposition header.
   *
   * @return the string representation of the Content-Disposition header
   */
  @Override
  public String toString() {
    return "Text Attachment\n" + value;
  }
}
