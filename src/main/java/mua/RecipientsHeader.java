package mua;

import java.util.ArrayList;
import java.util.List;
import utils.ASCIICharSequence;
import java.util.Iterator;

/** Represents the recipients header of a message, using a Recipients object. */
public final class RecipientsHeader implements Header<List<Address>> {
  /** The type of the header */
  private static final String TYPE = "To";
  /** The value of the Recipients header, represented as a Recipients object */
  private final List<Address> addresses;

  /**
   * Constructs a RecipientsHeader object with the specified Recipients objects.
   *
   * @param recipients the Recipients object of the recipients.
   */
  public RecipientsHeader(String addressList) {
    this.addresses = new ArrayList<>();

    if (addressList.startsWith("To: ")) addressList = addressList.substring("To: ".length());

    String[] addressArray = addressList.split(", ");

    for (String addressString : addressArray)
      addAddress(Address.fromFullAddress(addressString));
  }

  /**
   * Constructs a RecipientsHeader object with the specified list of addresses.
   *
   * @param addresses the list of addresses
   */
  public RecipientsHeader(List<Address> addresses) {
    this.addresses = addresses;
  }

  /**
   * Adds an address to the recipients list.
   *
   * @param address The address to be added.
   */
  public void addAddress(Address address) {
    this.addresses.add(address);
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
   * Returns the Recipients object for the recipients.
   *
   * @return the Recipients object for the recipients.
   */
  @Override
  public List<Address> getValue() {
    return addresses;
  }

  /**
   * Returns the ASCII representation of the Recipients object. The ASCII representation of the
   * Recipients object is the concatenation of the ASCII representations of its addresses. Each
   * address is separated by a comma and a space.
   *
   * @return the ASCII representation of the Recipients object
   */
  @Override
  public ASCIICharSequence encodeToASCII() {
    StringBuilder sb = new StringBuilder();
    Iterator<Address> iterator = addresses.iterator();

    while (iterator.hasNext()) {
      Address address = iterator.next();

      sb.append(address.encodeToASCII());

      if (iterator.hasNext()) sb.append(", ");
    }

    return ASCIICharSequence.of(TYPE + ": " + sb.toString());
  }

  /**
   * Encodes the Recipients Header's value to its UI representation, in a string format.
   * The UI representation is the representation of the header's value that needs to be displayed
   * to the user when creating cards or tables.
   * This consists of the UI representation of each address in the list, separated by a newline.
   *
   * @param extended whether to return the extended version of the header
   * @return the UI representation of the header
   */
  @Override
  public String encodeUIValue(boolean extended) {
    StringBuilder sb = new StringBuilder();
    Iterator<Address> iterator = addresses.iterator();

    while (iterator.hasNext()) {
      Address address = iterator.next();

      sb.append(address.encodeToUI(extended));

      if (iterator.hasNext()) sb.append("\n");
    }

    return sb.toString();
  }

  /**
   * Encodes the Recipients Header's name to its UI representation, in a string format.
   * The UI representation is the representation of the header's name that needs to be displayed to the user
   * when creating cards or tables.
   * The String generated matches the name of the header.
   *
   * @return the UI representation of the Recipients Header
   */
  @Override
  public String encodeUIName() {
    return TYPE;
  }
}
