package mua;

/** Represents an exception that is thrown when a header is missing from a message. */
@SuppressWarnings("serial")
public class MissingHeaderException extends RuntimeException {
  /**
   * Constructs a MissingHeaderException object with the specified message.
   *
   * @param message the message
   */
  public MissingHeaderException(String message) {
    super(message);
  }
}
