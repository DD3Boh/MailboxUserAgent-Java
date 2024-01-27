package clients;

import java.util.Scanner;
import mua.Subject;

public class SubjectEncode {

  /**
   * Tests subject value encoding
   *
   * <p>Reads a line from stdin containing the value of a subject header and emits its encoded
   * version in the stdout.
   *
   * @param args not used.
   */
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    String word = scanner.nextLine();
    scanner.close();

    Subject subject = new Subject(word);

    System.out.println(subject.encodeToAscii());
  }
}
