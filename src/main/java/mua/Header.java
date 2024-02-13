package mua;

import utils.ASCIICharSequence;

/** Represents a generic interface for message headers. */
public interface Header<T> {
  /**
   * Returns the type of the header.
   *
   * @return the type of the header
   */
  String getType();

  /**
   * Returns the value of the header.
   *
   * @return the value of the header
   */
  T getValue();

  /**
   * Returns an ASCII representation of the header.
   *
   * @return an ASCII representation of the header
   */
  ASCIICharSequence encodeToASCII();

  /**
   * Encode the header value to its UI representation, in a String format. The UI representation is
   * the representation of the header that needs to be displayed to the user when creating cards or
   * tables.
   *
   * <p>The default implementation returns an empty string, meaning that the header does not have a UI
   * representation.
   *
   * @param extended whether to return the extended version of the header
   * @return the UI representation of the header
   */
  default String encodeUIValue(boolean extended) {
    return "";
  }
  ;

  /**
   * Encode the header's name to its UI representation, in a String format. The UI representation is
   * the representation of the header that needs to be displayed to the user when creating cards or
   * tables.
   *
   * <p>The default implementation returns an empty string, meaning that the header does not have a UI
   * representation, hence the name is not displayed.
   *
   * @return the UI representation of the header's name
   */
  default String encodeUIName() {
    return "";
  }
  ;
}
