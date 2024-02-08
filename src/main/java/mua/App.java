package mua;

import utils.*;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;

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
  public static void main(String[] args) throws IOException {
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
            ui.output(getMailboxeString(new ArrayList<>(mailboxManager.getMailboxMap().keySet())));
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
          case "#":
            break;
          default:
            ui.error("Unknown command: " + input[0]);
            break;
        }
      }
    }
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
      Date date = (Date) message.getParts().get(0).getHeader(DateHeader.class).getValue();
      Address from = (Address) message.getParts().get(0).getHeader(SenderHeader.class).getValue();
      Recipients to = (Recipients) message.getParts().get(0).getHeader(RecipientsHeader.class).getValue();
      Subject subject = (Subject) message.getParts().get(0).getHeader(SubjectHeader.class).getValue();
      StringBuilder sb = new StringBuilder();
      for (Address recipient : to) {
        sb.append(recipient.local);
        sb.append("@");
        sb.append(recipient.domain);
        sb.append("\n");
      }
      String toStr = sb.toString();
      rows.add(List.of(
        date.date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd\nHH:mm:ss")),
        from.local + "@" + from.domain,
        toStr,
        Subject.decodeFromAscii(subject.subject)
        ));
    }

    return UITable.table(headers, rows, true, true);
  }

  /**
   * Returns the list of mailboxes as a String.
   *
   * @param mailboxes the list of mailboxes
   */
  public static String getMailboxeString(List<Mailbox> mailboxes) {
    List<String> headers = new ArrayList<>(List.of("Mailbox", "# messages"));
    List<List<String>> rows = new ArrayList<>();

    for (Mailbox mailbox : mailboxes) {
      rows.add(List.of(mailbox.name, Integer.toString(mailbox.getMessages().size())));
    }

    return UITable.table(headers, rows, true, false);
  }
}
