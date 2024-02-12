package mua;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import utils.*;

/**
 * The MailboxManager class represents a manager for mailboxes and keeps synchronized the mailboxe
 * and the storage.
 * It provides methods to add and remove mailboxes.
 */
public final class MailboxManager {
  /* Abstraction Function:
   * Represents a manager for mailboxes. It has the following parts:
   * - mailboxMap: a map of mailboxes and their corresponding Storage.Box
   * - messageMap: a map of messages and their corresponding Storage.Box.Entry
   * The manager can be modified by adding or removing mailboxes and messages.
   *
   * Representation Invariant:
   * - mailboxMap is not null and does not contain null keys or values
   * - messageMap is not null and does not contain null keys or values
   */

  /** Map of mailboxes and their corresponding Storage.Box */
  private final Map<Mailbox, Storage.Box> mailboxMap;

  /** Map of messages and their corresponding Storage.Box.Entry */
  private final Map<Message, Storage.Box.Entry> messageMap;

  /**
   * Constructs a new MailboxManager object with the given storage.
   *
   * @param storage the storage element of the root directory of the mailboxes.
   * @throws IllegalArgumentException if the storage is null
   */
  public MailboxManager(Storage storage) {
    mailboxMap = new LinkedHashMap<>();
    messageMap = new LinkedHashMap<>();

    if (storage == null) throw new IllegalArgumentException("The storage cannot be null");

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
   * Deletes a message from the mailbox. The message is also removed from the storage.
   *
   * @param mailbox the mailbox from which the message is to be deleted
   * @param message the message to be deleted
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
   * @param mailbox the mailbox to which the message is to be added
   * @param message the message to be added
   */
  public void addMessage(Mailbox mailbox, Message message) {
    Storage.Box storageBox = mailboxMap.get(mailbox);
    Storage.Box.Entry entry = storageBox.entry(message.encodeToASCII());
    messageMap.put(message, entry);
    mailbox.addMessage(message);
  }

  /**
   * Returns a copy of the Map of mailboxes and their corresponding Storage.Box.
   *
   * @return the Map of mailboxes and their corresponding Storage.Box
   */
  public Map<Mailbox, Storage.Box> getMailboxMap() {
    return new LinkedHashMap<>(mailboxMap);
  }

  /**
   * Returns a copy of the Map of messages and their corresponding Storage.Box.Entry.
   *
   * @return the Map of messages and their corresponding Storage.Box.Entry
   */
  public Map<Message, Storage.Box.Entry> getMessageMap() {
    return new LinkedHashMap<>(messageMap);
  }
}
