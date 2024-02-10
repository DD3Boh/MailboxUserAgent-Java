package mua;

import utils.*;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;
import java.time.ZonedDateTime;

/** The application class */
public class App {
  /**
   * Runs the REPL.
   *
   * <p>Develop here the REPL, see the README.md for more details.
   *
   * @param args the first argument is the mailbox base directory.
   * @throws IOException
   */
  public static void main(String[] args) throws IOException, MissingHeaderException {
    if (args.length <= 0)
      return;

    String mailboxBaseDir = args[0];
    Storage storage = new Storage(mailboxBaseDir);
    MailboxManager mailboxManager = new MailboxManager(storage);

    startREPL(mailboxManager);
  }

  /**
   * Starts the REPL.
   *
   * <p>Reads commands from the standard input and executes them.
   */
  public static void startREPL(MailboxManager mailboxManager) throws IOException {
    Mailbox curMailbox = null;
    try (UIInteract ui = UIInteract.getInstance()) {
      while (true) {
        String mailboxString;
        if (curMailbox == null) mailboxString = "*";
        else mailboxString = curMailbox.name;
        String prefix = "[" + mailboxString + "] > ";
        String[] input = ui.command(prefix);
        if (input == null) break;
        switch (input[0]) {
          case "LSM":
            if (curMailbox != null) {
              ui.error("LSM command is only available when no mailbox is selected.");
              break;
            }
            ui.output(getMailboxString(new ArrayList<>(mailboxManager.getMailboxMap().keySet())));
            break;
          case "LSE":
            if (curMailbox == null) {
              ui.error("No mailbox selected");
              break;
            }
            ui.output(getMessagesString(curMailbox.getMessages()));
            break;
          case "MBOX":
            if (input.length < 2) {
              ui.error("Usage: MBOX <mailbox>");
              break;
            }
            List<Mailbox> mailboxes = new ArrayList<Mailbox>(mailboxManager.getMailboxMap().keySet());
            curMailbox = mailboxes.get(Integer.parseInt(input[1]) - 1);
            break;
          case "READ":
            if (curMailbox == null) {
              ui.error("No mailbox selected");
              break;
            }
            if (input.length < 2) {
              ui.error("Usage: READ <message>");
              break;
            }
            List<Message> messages = curMailbox.getMessages();
            int index = Integer.parseInt(input[1]) - 1;
            if (index < 0 || index >= messages.size()) {
              ui.error("Invalid message index");
              break;
            }
            Message message = messages.get(index);
            ui.output(getMessageString(message));
            break;
          case "DELETE":
            if (curMailbox == null) {
              ui.error("No mailbox selected");
              break;
            }
            if (input.length < 2) {
              ui.error("Usage: DELETE <message>");
              break;
            }
            messages = curMailbox.getMessages();
            index = Integer.parseInt(input[1]) - 1;
            if (index < 0 || index >= messages.size()) {
              ui.error("Invalid message index");
              break;
            }
            message = messages.get(index);
            mailboxManager.deleteMessage(curMailbox, message);
            ui.prompt("Message deleted");
            break;
          case "COMPOSE":
            if (curMailbox == null) {
              ui.error("No mailbox selected");
              break;
            }
            handleCompose(mailboxManager, curMailbox, ui);
            break;
          case "#":
            break;
          default:
            ui.error("Unknown command: " + input[0]);
            break;
        }
      }
    }
  }

  private static void handleCompose(MailboxManager mailboxManager, Mailbox mailbox, UIInteract ui) throws IOException {
    List<String> headersStrings = new ArrayList<>(List.of("From", "To", "Subject", "Date"));
    List<Header<?>> headers = new ArrayList<>();
    List<MessagePart> parts = new ArrayList<>();
    String line = "";

    for (String headerString : headersStrings) {
      line = ui.line(headerString + ": ");
      headers.add(HeaderFactory.createHeader(headerString, line));
    }

    String body = "This is a message with multiple parts in MIME format.\n";
    parts.add(new MessagePart(headers, body));

    ui.prompt("Text Body (. to end): ");
    body = "";
    while ((line = ui.line()) != null && !line.equals("."))
      body += line + "\n";

    headers.clear();
    if (!body.isEmpty()) {
      if (ASCIICharSequence.isAscii(body)) {
        headers.add(new ContentTypeHeader("text/plain", "us-ascii", "frontier"));
      } else {
        headers.add(new ContentTypeHeader("text/plain", "utf-8", "frontier"));
        headers.add(new ContentTransferEncodingHeader("base64"));
      }
      parts.add(new MessagePart(headers, body));
    }

    ui.prompt("Html Body (. to end): ");

    body = "";
    headers.clear();

    while ((line = ui.line()) != null && !line.equals("."))
      body += line + "\n";

    if (!body.isEmpty()) {
      headers.add(new ContentTypeHeader("text/html", "utf-8", "frontier"));
      headers.add(new ContentTransferEncodingHeader("base64"));
      parts.add(new MessagePart(headers, body));
    }

    boolean hasAttachment = false;
    while (true) {
      headers.clear();
      String attachmentFilename = ui.line("Attachment filename (empty to stop): ");
      if (attachmentFilename.isEmpty()) break;

      headers.add(new ContentDispositionHeader("attachment", attachmentFilename));
      headers.add(new ContentTransferEncodingHeader("base64"));

      body = "";
      ui.prompt("Attachment (. to end): ");
      while ((line = ui.line()) != null && !line.equals("."))
        body += line + "\n";

      parts.add(new MessagePart(headers, body));
      hasAttachment = true;
    }

    if (parts.size() < 3) {
      MessagePart part0 = parts.get(0);
      MessagePart part1 = parts.get(1);
      MessagePart merged = MessagePart.mergeMessageParts(part0, part1);
      MessagePart part = new MessagePart(merged.getHeaders(), merged.getBodyDecoded());
      parts.remove(1);
      parts.set(0, part);
    } else {
      MessagePart part0 = parts.get(0);
      headers = new ArrayList<>(part0.getHeaders());
      headers.add(new MimeVersionHeader(Double.valueOf(1.0)));

      if (hasAttachment)
        headers.add(new ContentTypeHeader("multipart/mixed", null, "frontier"));
      else
        headers.add(new ContentTypeHeader("multipart/alternative", null, "frontier"));

      MessagePart part = new MessagePart(headers, part0.getBodyDecoded());
      parts.set(0, part);
    }

    Message message = new Message(parts);
    mailboxManager.addMessage(mailbox, message);
  }

  /**
   * Returns the list of messages as a String.
   *
   * @param messages the list of messages
   */
  public static String getMessagesString(List<Message> messages) {
    List<String> headers = new ArrayList<>(List.of("Date", "From", "To", "Subject"));
    List<List<String>> rows = new ArrayList<>();

    for (Message message : messages) {
      ZonedDateTime date = (ZonedDateTime) message.getParts().get(0).getHeader(DateHeader.class).getValue();
      Address from = (Address) message.getParts().get(0).getHeader(SenderHeader.class).getValue();
      Recipients to = (Recipients) message.getParts().get(0).getHeader(RecipientsHeader.class).getValue();
      String subject = (String) message.getParts().get(0).getHeader(SubjectHeader.class).getValue();
      StringBuilder sb = new StringBuilder();
      for (Address recipient : to) {
        sb.append(recipient.local);
        sb.append("@");
        sb.append(recipient.domain);
        sb.append("\n");
      }
      String toStr = sb.toString();
      rows.add(List.of(
        date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd\nHH:mm:ss")),
        from.local + "@" + from.domain,
        toStr,
        subject
        ));
    }

    return UITable.table(headers, rows, true, true);
  }

  /**
   * Returns the list of mailboxes as a String.
   *
   * @param mailboxes the list of mailboxes
   */
  public static String getMailboxString(List<Mailbox> mailboxes) {
    List<String> headers = new ArrayList<>(List.of("Mailbox", "# messages"));
    List<List<String>> rows = new ArrayList<>();

    for (Mailbox mailbox : mailboxes) {
      rows.add(List.of(mailbox.name, Integer.toString(mailbox.getMessages().size())));
    }

    return UITable.table(headers, rows, true, false);
  }

  /**
   * Returns a message as a String.
   *
   * @param message the message
   */
  public static String getMessageString(Message message) {
    List<String> regularHeaders = new ArrayList<>(List.of("From", "To", "Subject", "Date"));
    List<String> headersList = new ArrayList<>();
    List<String> values = new ArrayList<>();

    for (MessagePart part : message.getParts()) {
      for (Header<?> header : part.getHeaders()) {
        if (regularHeaders.contains(header.getType())) {
          headersList.add(header.getType());
          values.add(header.getValue().toString());
        } else if (header.getType().equals("Content-Type")) {
            headersList.add("Part\n" + header.getValue());
            values.add(part.getBodyDecoded());
        } else if (header.getType().equals("Content-Disposition")) {
            headersList.add("Text Attachment\n" + header.getValue());
            values.add(part.getBodyDecoded());
        }
      }
    }
    return UICard.card(headersList, values);
  }
}
