package clients;

import mua.*;
import utils.Storage;

import java.io.IOException;

/** MailboxList */
public class MailboxList {
  /**
   * Tests mailbox listing
   *
   * <p>Runs the app on the commands in the stdin, the commands are limited to: MBOX, LSM, LSE.
   *
   * @param args not used
   * @throws MissingHeaderException if a message is missing a header
   * @throws IOException if an I/O error occurs
   */
  public static void main(String[] args) throws IOException, MissingHeaderException {
    Storage storage = new Storage("tests/mbox");
    MailboxManager mailboxManager = new MailboxManager(storage);

    App.startREPL(mailboxManager);
  }
}
