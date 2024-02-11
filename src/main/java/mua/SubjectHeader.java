package mua;

import utils.ASCIICharSequence;
import utils.Base64Encoding;

/** Represents the subject header of a message. */
public final class SubjectHeader implements Header<String> {
  /** The value of the Subject header */
  private String value;

  /**
   * Constructs a SubjectHeader object with the specified String. The value is decoded if it is
   * encoded in Base64.
   *
   * @param value the subject of the message as a String.
   */
  public SubjectHeader(String value) {
    String subject;
    if (ASCIICharSequence.isAscii(value)) {
      ASCIICharSequence ascii = ASCIICharSequence.of(value);

      subject = Base64Encoding.decodeWord(ascii);

      if (subject == null) subject = ascii.toString();
    } else subject = value;

    this.value = subject;
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
  public String getValue() {
    return value;
  }

  /**
   * Returns an ASCII representation of the SubjectHeader object.
   *
   * @return an ASCII representation of the SubjectHeader object
   */
  @Override
  public ASCIICharSequence encodeToASCII() {
    ASCIICharSequence encodedSubject;
    if (ASCIICharSequence.isAscii(value)) encodedSubject = ASCIICharSequence.of(value);
    else encodedSubject = Base64Encoding.encodeWord(value);

    return ASCIICharSequence.of(getType() + ": " + encodedSubject);
  }

  /**
   * Returns a string representation of the SubjectHeader object.
   *
   * @return a string representation of the SubjectHeader object
   */
  @Override
  public String toString() {
    return value;
  }
}
