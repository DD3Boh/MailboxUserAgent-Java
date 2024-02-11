package mua;

import java.util.ArrayList;
import java.util.List;
import utils.ASCIICharSequence;
import java.util.Iterator;

/** Represents the recipients header of a message, using a Recipients object. */
public class RecipientsHeader implements Header<List<Address>> {
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
    return "To";
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

    return ASCIICharSequence.of(getType() + ": " + sb.toString());
  }

  /**
   * Returns a string representation of the Recipients object. The string includes all the addresses
   * in the Recipients object, separated by commas.
   *
   * @return a string representation of the Recipients object
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    Iterator<Address> iterator = addresses.iterator();

    while (iterator.hasNext()) {
      Address address = iterator.next();

      sb.append(address);

      if (iterator.hasNext()) sb.append("\n");
    }

    return sb.toString();
  }
}
