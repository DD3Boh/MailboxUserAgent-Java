package clients;

import java.util.Scanner;
import utils.ASCIICharSequence;
import mua.Subject;

public class SubjectDecode {

  /**
   * Tests subject value decoding
   *
   * <p>Reads a line from stdin containing the encoding of the value of a subject header and emits
   * its decoded version in the stdout.
   *
   * @param args not used.
   */
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    String word = scanner.nextLine();

    ASCIICharSequence ascii = ASCIICharSequence.of(word);

    System.out.println(new Subject(ascii).decodeFromAscii());
    scanner.close();
  }
}
