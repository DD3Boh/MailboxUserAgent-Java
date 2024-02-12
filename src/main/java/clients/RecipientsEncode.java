package clients;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import mua.Address;
import mua.RecipientsHeader;

/** RecipientsEncode */
public class RecipientsEncode {

  /**
   * Tests recipients encoding
   *
   * <p>Reads a series of lines from stidn, each containing a comma separated list of three strings
   * corresponding to the (possibly empty) <em>display name</em>, <em>local</em>, and
   * <em>domain</em> parts of an address and emits a line in stdout containing the encoding of the
   * recipients header obtained using such addresses.
   *
   * @param args not used.
   */
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    List<Address> recipients = new ArrayList<>();

    while (scanner.hasNextLine()) {
      String[] address = scanner.nextLine().split(", ");
      if (address.length == 3) recipients.add(new mua.Address(address[0], address[1], address[2]));
    }

    System.out.println(new RecipientsHeader(recipients).encodeToASCII());

    scanner.close();
  }
}
