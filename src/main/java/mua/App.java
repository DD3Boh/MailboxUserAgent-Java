package mua;

import utils.*;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;

/** The application class */
public class App {
  static private MailboxManager mailboxManager;

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
    mailboxManager = new MailboxManager(storage);

    try (UIInteract ui = UIInteract.getInstance()) {
      while (true) {
        String prefix = "> ";
        String[] input = ui.command(prefix);
        if (input == null) break;
        switch (input[0]) {
          case "LSM":
            printMailBoxes(new ArrayList<>(mailboxManager.getMailboxMap().keySet()));
            break;
          case "MBOX":
            ui.output("You requested a cd...");
            break;
          default:
            ui.error("Unknown command: " + input[0]);
            break;
        }
      }
    }
  }

  /**
   * Prints the list of mailboxes to the standard output.
   *
   * @param mailboxes the list of mailboxes
   */
  public static void printMailBoxes(List<Mailbox> mailboxes) {
    List<String> headers = new ArrayList<>(List.of("Mailbox", "# messages"));
    List<List<String>> rows = new ArrayList<>();

    for (Mailbox mailbox : mailboxes) {
      rows.add(List.of(mailboxManager.getMailboxMap().get(mailbox).toString(), Integer.toString(mailbox.getMessages().size())));
    }

    System.out.println(UITable.table(headers, rows, true, false));
  }
}
