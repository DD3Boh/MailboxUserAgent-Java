package clients;

import java.util.Scanner;
import mua.Date;

public class DateEncode {

  /**
   * Tests date encoding
   *
   * <p>Reads three integers from stdin corresponding to an year, month and day and emits a line in
   * the stout containing the encoding of the date corresponding to the exact midnight of such date
   * (in the "Europe/Rome" timezone).
   *
   * @param args not used.
   */
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int year = scanner.nextInt();
    int month = scanner.nextInt();
    int day = scanner.nextInt();
    scanner.close();

    Date date = new Date(year, month, day);

    System.out.println(date);
  }
}
