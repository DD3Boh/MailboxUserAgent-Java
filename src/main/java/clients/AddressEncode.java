package clients;

import java.util.Scanner;
import mua.Address;

public class AddressEncode {
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
