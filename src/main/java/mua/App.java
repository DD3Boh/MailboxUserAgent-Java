package mua;

import utils.ASCIICharSequence;
import utils.EntryEncoding;
import utils.Fragment;
import utils.Storage;
import utils.UIInteract;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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

    for (Storage.Box box : storage.boxes()) {
      System.out.println(box);
    }

    try (UIInteract ui = UIInteract.getInstance()) {
      for (;;) {
        String[] input = ui.command("> ");
        if (input == null) break;
        switch (input[0]) {
          case "LSM":
            ui.output("You requested an lsm...");
            printMailboxes(new ArrayList<>(mailboxManager.getMailboxMap().keySet()));
            break;
          case "CD":
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
  public static void printMailboxes(List<Mailbox> mailboxes) {
    for (Mailbox mailbox : mailboxes) {
      System.out.println(mailboxManager.getMailboxMap().get(mailbox));
    }
  }
}
