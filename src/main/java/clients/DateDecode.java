package clients;

import java.util.Scanner;

import mua.DateHeader;

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

    DateHeader header = new DateHeader(input);

    System.out.println(header.getValue().getDayOfWeek());
  }
}
