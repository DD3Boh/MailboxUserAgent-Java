package mua;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import utils.ASCIICharSequence;

/** Represents a recipients header, using a List of Address. */
public final class RecipientsHeader implements Header<List<Address>> {
  /*
   * Abstraction Function:
   * Represents a recipients header, with value being a list of addresses.
   * The value is represented as a List of Address.
   *
   * Representation Invariant:
   * - The addresses is not null and does not contain null elements.
   * - The addresses cannot be empty.
   * - The addresses cannot contain duplicate elements.
   */
  /** The type of the header */
  private static final String TYPE = "To";

  /** The value of the Recipients header, represented as a Recipients object */
  private final List<Address> addresses;

  /**
   * Constructs a RecipientsHeader object with the specified String, which contains a list of
   * addresses separated by a comma and a space. If the string starts with "To: ", the "To: " is
   * removed. The string is then split by ", " to obtain the list of addresses.
   *
   * @param addressList the list of addresses, separated by a comma and a space
   * @throws IllegalArgumentException if the addressList is null or empty
   * @throws IllegalArgumentException if the addressList does not contain at least one address
   */
  public RecipientsHeader(String addressList) {
    this.addresses = new ArrayList<>();

    if (addressList == null || addressList.isEmpty())
      throw new IllegalArgumentException("The address list cannot be null or empty");

    if (addressList.startsWith("To: ")) addressList = addressList.substring("To: ".length());

    String[] addressArray = addressList.split(", ");

    if (addressArray.length == 0)
      throw new IllegalArgumentException("The address list cannot be empty");

    for (String addressString : addressArray) addAddress(Address.fromFullAddress(addressString));
  }

  /**
   * Constructs a RecipientsHeader object with the specified list of addresses.
   *
   * @param addresses the list of addresses
   * @throws IllegalArgumentException if the addresses is null
   * @throws IllegalArgumentException if the addresses is empty
   * @throws IllegalArgumentException if the addresses contains null elements
   */
  public RecipientsHeader(List<Address> addresses) {
    if (addresses == null) throw new IllegalArgumentException("The addresses cannot be null");
    if (addresses.isEmpty()) throw new IllegalArgumentException("The addresses cannot be empty");
    if (addresses.contains(null))
      throw new IllegalArgumentException("The addresses cannot contain null elements");

    this.addresses = addresses;
  }

  /**
   * Adds an address to the recipients list.
   *
   * @param address The address to be added.
   * @throws IllegalArgumentException if the address is null
   */
  public void addAddress(Address address) {
    if (address == null) throw new IllegalArgumentException("The address cannot be null");
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
   * Encodes the Recipients Header's value to its UI representation, in a string format. The UI
   * representation is the representation of the header's value that needs to be displayed to the
   * user when creating cards or tables. This consists of the UI representation of each address in
   * the list, separated by a newline.
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
   * Encodes the Recipients Header's name to its UI representation, in a string format. The UI
   * representation is the representation of the header's name that needs to be displayed to the
   * user when creating cards or tables. The String generated matches the name of the header.
   *
   * @return the UI representation of the Recipients Header
   */
  @Override
  public String encodeUIName() {
    return TYPE;
  }
}
