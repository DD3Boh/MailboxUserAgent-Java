package clients;

import java.util.Scanner;

import mua.SubjectHeader;

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

    SubjectHeader subject = new SubjectHeader(word);

    System.out.println(subject);
    scanner.close();
  }
}
