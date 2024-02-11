package mua;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/** Represents a mailbox that stores messages. */
public final class Mailbox {
  /** The name of the mailbox */
  public final String name;

  /** The list of messages stored in the mailbox */
  private final List<Message> messages;

  /**
   * Constructs a Mailbox object with the given list of messages.
   *
   * @param messages the list of messages to be stored in the mailbox
   * @param name the name of the mailbox
   */
  public Mailbox(List<Message> messages, String name) {
    this.messages = messages;
    this.name = name;
  }

  /**
   * Returns the list of messages sorted by date in descending order.
   *
   * @return the sorted list of messages
   */
  public List<Message> getMessages() {
    List<Message> sortedMessages = new ArrayList<>(messages);
    Collections.sort(
        sortedMessages,
        new Comparator<Message>() {
          @Override
          public int compare(Message m1, Message m2) {
            ZonedDateTime date1 =
                (ZonedDateTime) m1.getParts().get(0).getHeader(DateHeader.class).getValue();
            ZonedDateTime date2 =
                (ZonedDateTime) m2.getParts().get(0).getHeader(DateHeader.class).getValue();
            return date2.compareTo(date1);
          }
        });
    return sortedMessages;
  }

  /**
   * Adds a message to the mailbox.
   *
   * @param message the message to be added
   */
  public void addMessage(Message message) {
    messages.add(message);
  }

  /**
   * Removes a message from the mailbox.
   *
   * @param message the message to be removed
   */
  public void removeMessage(Message message) {
    messages.remove(message);
  }

  /**
   * Removes a message at the specified index from the mailbox.
   *
   * @param index the index of the message to be removed
   */
  public void removeMessage(int index) {
    messages.remove(index);
  }
}
