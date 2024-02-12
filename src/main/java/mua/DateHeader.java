package mua;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import utils.ASCIICharSequence;
import utils.DateEncoding;

/** Represents the date header of when the message was composed. */
public final class DateHeader implements Header<ZonedDateTime> {
  /*
   * Abstraction Function:
   * Represents a Date header, with value being a date.
   *
   * Representation Invariant:
   * - value is not null or empty.
   */
  /** The type of the header */
  public static final String TYPE = "Date";

  /** The value of the Date header, represented as a ZonedDateTime object */
  private ZonedDateTime value;

  /**
   * Constructs a DateHeader object with the specified date and time when the message was composed.
   *
   * @param value a string in format RFC_1123_DATE_TIME representing the date and time when the
   *     message was composed.
   * @throws IllegalArgumentException if the value is null or empty
   */
  public DateHeader(String value) throws IllegalArgumentException {
    if (value == null || value.isEmpty())
      throw new IllegalArgumentException("The value cannot be null or empty");
    this.value = DateEncoding.decode(ASCIICharSequence.of(value));
  }

  /**
   * Constructs a DateHeader object with the specified ZonedDateTime object.
   *
   * @param value the date and time when the message was composed, represented as a ZonedDateTime
   *     object.
   */
  public DateHeader(ZonedDateTime value) {
    this.value = value;
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
   * Returns the date and time when the message was composed.
   *
   * @return the date and time when the message was composed.
   */
  @Override
  public ZonedDateTime getValue() {
    return value;
  }

  /**
   * Returns an ASCII representation of the DateHeader object.
   *
   * @return an ASCII representation of the DateHeader object
   */
  @Override
  public ASCIICharSequence encodeToASCII() {
    return ASCIICharSequence.of(TYPE + ": " + DateEncoding.encode(value));
  }

  /**
   * Encodes the Date Header's value to its UI representation, in a String format. The UI
   * representation is the representation of the header that needs to be displayed to the user when
   * creating cards or tables. The compressed version of the header returns a date encoded like
   * "YYYY-MM-DD\nHH:MM:SS". The extended version of the header returns a date encoded like
   * "YYYY-MM-DDTHH:MM:SS+OFFSET", where OFFSET is the offset from UTC. This matches the ISO 8601
   * format.
   *
   * @param extended whether to return the extended version of the header
   * @return the UI representation of the header
   */
  @Override
  public String encodeUIValue(boolean extended) {
    if (extended) return value.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    else return value.format(DateTimeFormatter.ofPattern("yyyy-MM-dd\nHH:mm:ss"));
  }

  /**
   * Encodes the Date Header's name to its UI representation, in a String format. The UI
   * representation is the representation of the header that needs to be displayed to the user when
   * creating cards or tables.
   *
   * @return the UI representation of the header's name
   */
  @Override
  public String encodeUIName() {
    return TYPE;
  }
}
