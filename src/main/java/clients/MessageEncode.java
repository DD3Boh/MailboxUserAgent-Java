package clients;

import mua.*;
import utils.ASCIICharSequence;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

/** MessageEncode */
public class MessageEncode {

  public static final ZonedDateTime DATE =
      ZonedDateTime.of(2023, 12, 6, 12, 30, 20, 200, ZoneId.of("Europe/Rome"));

  /**
   * Tests message encoding
   *
   * <p>Reads a message from stdin and emits its encoding on the stdout.
   *
   * <p>The stdin contains:
   *
   * <ul>
   *   <li>the sender address (three lines, see {@link AddressDecode}),
   *   <li>two recipient addresses (three lines each, as above),
   *   <li>the subject (one line),
   *   <li>the text part (one line, possibly empty),
   *   <li>the HTML part (one line, possibly empty).
   * </ul>
   *
   * To such information, the program adds the date corresponding to {@link #DATE}.
   *
   * @param args not used
   */
  public static void main(String[] args) {
    String displayName, local, domain;
    List<Header<?>> headers = new ArrayList<>();
    List<MessagePart> parts = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);

    displayName = scanner.nextLine();
    local = scanner.nextLine();
    domain = scanner.nextLine();

    headers.add(new SenderHeader(new Address(displayName, local, domain)));

    displayName = scanner.nextLine();
    local = scanner.nextLine();
    domain = scanner.nextLine();

    Recipients recipients = new Recipients();
    recipients.addAddress(new Address(displayName, local, domain));

    displayName = scanner.nextLine();
    local = scanner.nextLine();
    domain = scanner.nextLine();
    recipients.addAddress(new Address(displayName, local, domain));

    headers.add(new RecipientsHeader(recipients));

    String subject = scanner.nextLine();
    headers.add(new SubjectHeader(subject));

    headers.add(new DateHeader(DATE.format(DateTimeFormatter.RFC_1123_DATE_TIME)));

    String text = scanner.nextLine();
    String html = scanner.nextLine();
    scanner.close();

    if (!text.isEmpty() && !html.isEmpty()) {
      headers.add(new MimeVersionHeader(1.0));
      headers.add(new ContentTypeHeader("multipart/alternative", null, "frontier"));
      parts.add(new MessagePart(headers, "This is a message with multiple parts in MIME format."));
      headers.clear();
      if (ASCIICharSequence.isAscii(text)) {
        headers.add(new ContentTypeHeader("text/plain", "us-ascii", "frontier"));
      } else {
        headers.add(new ContentTypeHeader("text/plain", "utf-8", "frontier"));
        headers.add(new ContentTransferEncodingHeader("base64"));
      }
      parts.add(new MessagePart(headers, text));
      headers.clear();
      headers.add(new ContentTypeHeader("text/html", "utf-8", "frontier"));
      headers.add(new ContentTransferEncodingHeader("base64"));
      parts.add(new MessagePart(headers, html));
    } else if (!text.isEmpty()) {
      if (ASCIICharSequence.isAscii(text)) {
        headers.add(new ContentTypeHeader("text/plain", "us-ascii", null));
        parts.add(new MessagePart(headers, text));
      } else {
        headers.add(new ContentTypeHeader("text/plain", "utf-8", null));
        headers.add(new ContentTransferEncodingHeader("base64"));
        parts.add(new MessagePart(headers, text));
      }
    } else if (!html.isEmpty()) {
      headers.add(new ContentTypeHeader("text/html", null, null));
      headers.add(new ContentTransferEncodingHeader("base64"));
      parts.add(new MessagePart(headers, html));
    }

    System.out.println(new Message(parts).encodeToASCII());
  }

}
