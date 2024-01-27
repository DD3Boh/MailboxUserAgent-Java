package clients;

import mua.Recipients;

import java.util.Scanner;

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

    Recipients recipients = new Recipients();

    while (scanner.hasNextLine())
      recipients.addAddress(scanner.nextLine());

    System.out.println(recipients);

    scanner.close();
  }
}
