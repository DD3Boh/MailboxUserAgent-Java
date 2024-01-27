package clients;

import java.util.Scanner;
import mua.Address;

public class AddressEncode {

  /**
   * Tests address encoding
   *
   * <p>Reads three lines from stdin corresponding to the (possibly empty) <em>display name</em>,
   * <em>local</em>, and <em>domain</em> parts of the address and emits a line in the stout
   * containing the encoding of the email address.
   *
   * @param args not used.
   */
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    String displayName = scanner.nextLine();
    String local = scanner.nextLine();
    String domain = scanner.nextLine();

    Address address = new Address(displayName, local, domain);

    System.out.println(address);

    scanner.close();
  }
}
