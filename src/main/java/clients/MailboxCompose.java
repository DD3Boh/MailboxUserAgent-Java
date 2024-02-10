package clients;

import java.io.IOException;
import mua.App;
import mua.MailboxManager;
import mua.MissingHeaderException;
import utils.Storage;

/** MailboxCompose */
public class MailboxCompose {

  /**
   * Tests message composition and deletion
   *
   * <p>Runs the app on the commands in the stdin, the commands are limited to: MBOX, COMPOSE, READ,
   * DELTE.
   *
   * @param args not used
   */
  public static void main(String[] args) throws IOException, MissingHeaderException {
    Storage storage = new Storage("tests/mbox");
    MailboxManager mailboxManager = new MailboxManager(storage);

    App.startREPL(mailboxManager);
  }
}
