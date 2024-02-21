package mua;

import utils.ASCIICharSequence;
import utils.Base64Encoding;

/** Represents the subject header of a message. */
public final class SubjectHeader implements Header {
  /*
   * Abstraction Function:
   * Represents a Subject header, with value being the subject of the message.
   * The value is represented as a String.
   * If the value is encoded in Base64, it is decoded to a String before being stored.
   * If the value is not encoded in Base64, it is stored as is.
   * When the header is encoded to ASCII, the value is encoded in Base64 if it contains non-ASCII characters,
   * and stored as is if it contains only ASCII characters.
   *
   * Representation Invariant:
   * - value is not null or empty.
   */
  /** The type of the header */
  public static final String TYPE = "Subject";

  /** The value of the Subject header */
  private final String value;

  /**
   * Constructs a SubjectHeader object with the specified String. The value is decoded if it is
   * encoded in Base64.
   *
   * @param value the subject of the message as a String.
   * @throws IllegalArgumentException if the value is null or empty
   */
  public SubjectHeader(String value) {
    String subject;
    if (value == null || value.isEmpty())
      throw new IllegalArgumentException("The value cannot be null or empty");

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
    return TYPE;
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
   * Returns an ASCII representation of the SubjectHeader object. The ASCII representation is of the
   * form "Subject: value".
   *
   * @return an ASCII representation of the SubjectHeader object
   */
  @Override
  public ASCIICharSequence encodeToASCII() {
    ASCIICharSequence encodedSubject;
    if (ASCIICharSequence.isAscii(value)) encodedSubject = ASCIICharSequence.of(value);
    else encodedSubject = Base64Encoding.encodeWord(value);

    return ASCIICharSequence.of(TYPE + ": " + encodedSubject);
  }

  /**
   * Encodes the Subject Header's value to its UI representation, in a string format. The UI
   * representation is the representation of the header's value that needs to be displayed to the
   * user when creating cards or tables. This header does not have an extended version, so the
   * extended parameter is ignored. The UI representation of the header consists of the value of the
   * header, as an UTF-8 string.
   *
   * @param extended ignored
   * @return the UI representation of the header
   */
  @Override
  public String encodeUIValue(boolean extended) {
    return value;
  }

  /**
   * Encodes the Subject Header's name to its UI representation, in a string format. The UI
   * representation is the representation of the header's name that needs to be displayed to the
   * user when creating cards or tables. The String generated is of the form "Subject\nvalue".
   *
   * @param isExtended ignored
   * @return the UI representation of the Subject Header
   */
  @Override
  public String encodeUIName(boolean isExtended) {
    return TYPE;
  }
}
