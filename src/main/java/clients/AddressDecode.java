package clients;

import java.util.Scanner;
import mua.Address;

public class AddressDecode {
  /**
   * Tests address decoding
   *
   * <p>Reads a line from stdin containing the encoding of an email address and emits three lines in
   * the stout corresponding to the (possibly empty) <em>display name</em>, <em>local</em>, and
   * <em>domain</em> parts of the address.
   *
   * @param args not used.
   */
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    String encodedAddress = scanner.nextLine();
    scanner.close();

    Address address = Address.fromFullAddress(encodedAddress);
    String displayName = address.displayName;
    String local = address.local;
    String domain = address.domain;

    System.out.println(displayName);
    System.out.println(local);
    System.out.println(domain);
  }
}
