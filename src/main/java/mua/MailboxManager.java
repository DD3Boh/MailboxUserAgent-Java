package mua;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import utils.*;

/**
 * The MailboxManager class represents a manager for mailboxes. It provides methods to add and
 * remove mailboxes.
 */
public class MailboxManager {
  /** Map of mailboxes and their corresponding Storage.Box */
  private final Map<Mailbox, Storage.Box> mailboxMap;

  /** Map of messages and their corresponding Storage.Box.Entry */
  private final Map<Message, Storage.Box.Entry> messageMap;

  /**
   * Constructs a new MailboxManager object with the given storage.
   *
   * @param storage the storage element of the root directory of the mailboxes.
   * @throws MissingHeaderException if the first part of a message does not contain the From, To,
   *     Subject, and Date headers
   */
  public MailboxManager(Storage storage) throws MissingHeaderException {
    mailboxMap = new LinkedHashMap<>();
    messageMap = new LinkedHashMap<>();

    for (Storage.Box storageBox : storage.boxes()) {
      List<Message> messages = new ArrayList<>();
      for (Storage.Box.Entry entry : storageBox.entries()) {
        ASCIICharSequence sequence = entry.content();
        List<Fragment> fragments = EntryEncoding.decode(sequence);
        Message message = new Message(Message.createMessageParts(fragments));
        messages.add(message);
        messageMap.put(message, entry);
      }
      Mailbox mailbox = new Mailbox(messages, storageBox.toString());
      mailboxMap.put(mailbox, storageBox);
    }
  }

  /**
   * Deletes a message from the mailbox.
   *
   * @param mailbox the mailbox
   * @param message the message
   */
  public void deleteMessage(Mailbox mailbox, Message message) {
    Storage.Box.Entry entry = messageMap.get(message);
    messageMap.remove(message);
    mailbox.removeMessage(message);
    entry.delete();
  }

  /**
   * Adds a message to the mailbox.
   *
   * @param mailbox the mailbox
   * @param message the message
   */
  public void addMessage(Mailbox mailbox, Message message) {
    Storage.Box storageBox = mailboxMap.get(mailbox);
    Storage.Box.Entry entry = storageBox.entry(message.encodeToASCII());
    messageMap.put(message, entry);
    mailbox.addMessage(message);
  }

  /**
   * Returns the Map of mailboxes and their corresponding Storage.Box.
   *
   * @return the Map of mailboxes and their corresponding Storage.Box
   */
  public Map<Mailbox, Storage.Box> getMailboxMap() {
    return Collections.unmodifiableMap(mailboxMap);
  }

  /**
   * Returns the Map of messages and their corresponding Storage.Box.Entry.
   *
   * @return the Map of messages and their corresponding Storage.Box.Entry
   */
  public Map<Message, Storage.Box.Entry> getMessageMap() {
    return Collections.unmodifiableMap(messageMap);
  }
}
