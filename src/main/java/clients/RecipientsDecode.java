package clients;

import mua.Address;
import mua.Recipients;
import java.util.Scanner;

public class RecipientsDecode {

  /**
   * Tests recipients decoding
   *
   * <p>Reads a line from stdin containing the encoding of the recipients header and for every
   * address in the header emits a line in stdout containing a comma separated list of three strings
   * corresponding to the (possibly empty) <em>display name</em>, <em>local</em>, and
   * <em>domain</em> parts of the address.
   *
   * @param args not used.
   */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String encodedHeader = scanner.nextLine();
        scanner.close();

        Recipients recipients = new Recipients(encodedHeader);

        for (Address address : recipients) {
            String displayName = address.getDisplayName();
            String localPart = address.getLocal();
            String domain = address.getDomain();

            System.out.println(displayName + ", " + localPart + ", " + domain);
        }
    }
}
