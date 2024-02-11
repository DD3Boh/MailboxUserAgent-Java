package mua;

import java.util.List;
import utils.ASCIICharSequence;
import utils.AddressEncoding;

/** Represents an email address with display name, local part, and domain. */
public class Address {
  /*
   * Abstraction Function:
   * Represents an email address with a display name, local part, and domain.
   * An instance a of Address represents an email address of the form "displayName local@domain".
   * If displayName is an empty string, it represents an email address of the form "local@domain".
   *
   * Representation Invariant:
   * - displayName, local, and domain are not null.
   * - local and domain are valid parts of an email address, as defined by AddressEncoding.isValidAddressPart(String).
   * - displayName can be an empty string.
   */

  /** the display name associated with the email address */
  public final String displayName;

  /** the local part of the email address (before the '@') */
  public final String local;

  /** domain the domain part of the email address (after the '@') */
  public final String domain;

  /**
   * Constructs a new Address instance from a display name, local part, and domain.
   *
   * @param displayName the display name
   * @param local the local part
   * @param domain the domain part
   * @throws IllegalArgumentException if the local part is invalid, according to
   *     AddressEncoding.isValidAddressPart(String)
   * @throws IllegalArgumentException if the domain part is invalid, according to
   *     AddressEncoding.isValidAddressPart(String)
   * @throws IllegalArgumentException if displayName, local, or domain are null.
   */
  public Address(String displayName, String local, String domain) throws IllegalArgumentException {
    if (displayName == null || local == null || domain == null)
      throw new IllegalArgumentException("Arguments cannot be null");

    if (!AddressEncoding.isValidAddressPart(local))
      throw new IllegalArgumentException("Invalid local part");

    if (!AddressEncoding.isValidAddressPart(domain))
      throw new IllegalArgumentException("Invalid domain part");

    this.displayName = displayName;
    this.local = local;
    this.domain = domain;
  }

  /**
   * Constructs a new Address instance from a String containing the full email address.
   *
   * The expected format is "Display Name local@domain". If the display name is not present, the
   * format should be "local@domain".
   * If the display name is present and is made of more than two words, it should be enclosed in
   * double quotes, like this: ""Display Long Name" local@domain".
   *
   * @param fullAddress the full email address string
   * @throws IllegalArgumentException if the email address is invalid, according to exceptions
   *     thrown by the helper methods
   */
  public static Address fromFullAddress(String fullAddress) throws IllegalArgumentException {
    ASCIICharSequence ascii = ASCIICharSequence.of(fullAddress);
    List<List<ASCIICharSequence>> addresses = AddressEncoding.decode(ascii);

    if (addresses.size() != 1 || addresses.get(0).size() != 3)
      throw new IllegalArgumentException("Invalid email address");

    String displayName = addresses.get(0).get(0).toString();
    String local = addresses.get(0).get(1).toString();
    String domain = addresses.get(0).get(2).toString();

    return new Address(displayName, local, domain);
  }

  /**
   * Returns the ASCII representation of the Address object, ready to be written to the disk.
   *
   * If the display name is not present, it returns the address in the format "local@domain".
   * If the display name is present and is made of one or two words, it returns the address
   * in the format "Display Name local@domain".
   * If the display name is present and is made of more than two words, it returns the address
   * in the format ""Display Long Name" local@domain".
   *
   * @return the ASCII representation of the Address object
   */
  public ASCIICharSequence encodeToASCII() {
    return ASCIICharSequence.of(this.toString());
  }

  /**
   * Returns the String representation of the Address object.
   * If the display name is not present, it returns the address in the format "local@domain".
   * If the display name is present and is made of one or two words, it returns the address
   * in the format "Display Name local@domain".
   * If the display name is present and is made of more than two words, it returns the address
   * in the format ""Display Long Name" local@domain".
   *
   * @return a String representation of the Address object
   */
  @Override
  public String toString() {
    if (displayName.trim().isBlank())
      return String.format("%s@%s", local, domain);

    String[] nameParts = displayName.split("\\s+");

    if (nameParts.length <= 2)
      return String.format("%s <%s@%s>", displayName, local, domain);
    else
      return String.format("\"%s\" <%s@%s>", displayName, local, domain);
  }
}
