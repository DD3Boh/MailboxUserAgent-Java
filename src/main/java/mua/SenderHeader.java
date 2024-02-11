package mua;

import utils.ASCIICharSequence;

/** Represents the sender header of a message, using an Address object. */
public final class SenderHeader implements Header<Address> {
  /** The type of the header */
  public static final String TYPE = "From";
  /** The value of the Sender header, represented as an Address object */
  private Address value;

  /**
   * Constructs a SenderHeader object with the specified Address object.
   *
   * @param value the Address object of the sender
   */
  public SenderHeader(Address value) {
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
   * Returns the Address object of the sender.
   *
   * @return the Address object of the sender.
   */
  @Override
  public Address getValue() {
    return value;
  }

  /**
   * Returns an ASCII representation of the SenderHeader object.
   *
   * @return an ASCII representation of the SenderHeader object
   */
  @Override
  public ASCIICharSequence encodeToASCII() {
    return ASCIICharSequence.of(TYPE + ": " + value.encodeToASCII());
  }

  /**
   * Encodes the Sender Header's value to its UI representation, in a string format.
   * The UI representation is the representation of the header's value that needs to be displayed to the user
   * when creating cards or tables.
   * This consists of the UI representation of the Address object in "value".
   *
   * @param extended whether to return the extended version of the header
   * @return the UI representation of the header
   */
  @Override
  public String encodeUIValue(boolean extended) {
    return value.encodeToUI(extended);
  }

  /**
   * Encodes the Sender Header's name to its UI representation, in a string format.
   * The UI representation is the representation of the header's name that needs to be displayed to the user
   * when creating cards or tables.
   * This header does not have an extended version, so the extended parameter is ignored.
   * The UI representation of the header's name is "From".
   *
   * @return the UI representation of the header's name
   */
  @Override
  public String encodeUIName() {
    return TYPE;
  }
}
