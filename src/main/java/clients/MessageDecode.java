package clients;

import java.util.List;
import java.util.Scanner;

import mua.*;
import utils.ASCIICharSequence;
import utils.EntryEncoding;
import utils.Fragment;

public class MessageDecode {

  /**
   * Tests message decoding
   *
   * <p>Reads a message from stdin and emits its fragments on the stdout.
   *
   * <p>Every fragment should be emitted as, for example:
   *
   * <pre>{@code
   * Fragment
   *   Raw headers:
   *     Raw type = from, value = Luca Prigioniero <prigioniero@di.unimi.it>
   *     Raw type = to, value = "Massimo prof. Santini" <santini@di.unimi.it>, info@unimi.it
   *     Raw type = subject, value = Oggetto semplice
   *     Raw type = date, value = Wed, 6 Dec 2023 12:30:20 +0100
   *     Raw type = mime-version, value = 1.0
   *     Raw type = content-type, value = multipart/alternative; boundary=frontier
   *   Raw body:
   *     This is a message with multiple parts in MIME format.
   * }</pre>
   *
   * @param args not used.
   */
  public static void main(String[] args) {
    StringBuilder rawMessage = new StringBuilder();
    Scanner scanner = new Scanner(System.in);

    // Read lines from stdin until an empty line is entered
    while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        rawMessage.append(line).append("\n");
    }
    scanner.close();

    ASCIICharSequence sequence = ASCIICharSequence.of(rawMessage.toString());
    List<Fragment> fragments = EntryEncoding.decode(sequence);
    Message message = new Message(Message.createMessageParts(fragments));

    for (MessagePart part : message.getParts()) {
      System.out.println("Fragment\n\tRaw headers:");
      for (Header<?> header : part.getHeaders()) {
        String headerRawType = header.getType().toString();
        System.out.println("\t\tRaw type = " + headerRawType.toLowerCase() + ", value = " + header.toString().replace(headerRawType + ": ", ""));
      }
      System.out.println("\tRaw body: \n\t\t" + part.body + "\n");
    }
  }
}
