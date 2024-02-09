package clients;

import java.io.IOException;

import mua.App;
import mua.MailboxManager;
import utils.Storage;

/** MailboxRead */
public class MailboxRead {
  /**
   * Tests message reading
   *
   * <p>Runs the app on the commands in the stdin, the commands are limited to: MBOX, READ.
   *
   * @param args not used
   */
  public static void main(String[] args) throws IOException {
    Storage storage = new Storage("tests/mbox");
    MailboxManager mailboxManager = new MailboxManager(storage);

    App.startREPL(mailboxManager);
  }
}
