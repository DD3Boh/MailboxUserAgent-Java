package clients;

import java.util.Scanner;

import mua.Date;

/** DateDecode */
public class DateDecode {

  /**
   * Tests date decoding
   *
   * <p>Reads a line from stdin containing the encoding of a date and emits the corresponding day of
   * week in the stout.
   *
   * @param args not used.
   */
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    String input = scanner.nextLine();
    scanner.close();

    Date date = new Date(input);

    System.out.println(date.getDayOfWeek());
  }
}
