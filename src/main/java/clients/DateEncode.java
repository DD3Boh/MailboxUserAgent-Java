package clients;

import java.util.Scanner;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import mua.DateHeader;
import utils.DateEncoding;

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

    String dateString = ZonedDateTime.of(year, month, day, 0, 0, 0, 0, DateEncoding.EUROPE_ROME).format(DateTimeFormatter.RFC_1123_DATE_TIME);
    DateHeader date = new DateHeader(dateString);

    System.out.println(date.encodeToASCII().toString().replace(date.getType() + ": ", ""));
  }
}
