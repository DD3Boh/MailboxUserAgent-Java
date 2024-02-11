package mua;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/** Represents a mailbox that stores messages. */
public final class Mailbox {
  /*
   * Abstraction Function:
   * Represents a mailbox that stores messages.
   * An instance a of Mailbox represents a mailbox with the name a.name and the list of messages a.messages.
   * The list of messages is sorted by date in descending order, when returned by getMessages(), using
   * the date header of the messages.
   * The mailbox can be modified by adding or removing messages.
   *
   * Representation Invariant:
   * - messages is not null
   * - messages is not empty
   * - name is not null
   * - name is not empty
   * - all messages in messages are not null, this is enforced by the constructor of Message
   */
  /** The name of the mailbox */
  public final String name;

  /** The list of messages stored in the mailbox */
  private final List<Message> messages;

  /**
   * Constructs a Mailbox object with the given list of messages and name.
   *
   * @param messages the list of messages to be stored in the mailbox
   * @param name the name of the mailbox
   * @throws IllegalArgumentException if the list of messages is null or empty
   * @throws IllegalArgumentException if the name is null or empty
   */
  public Mailbox(List<Message> messages, String name) throws IllegalArgumentException {
    if (messages == null) throw new IllegalArgumentException("Messages cannot be null");
    if (messages.isEmpty()) throw new IllegalArgumentException("Messages cannot be empty");
    if (name == null) throw new IllegalArgumentException("Name cannot be null");
    if (name.isBlank()) throw new IllegalArgumentException("Name cannot be empty");

    this.messages = new ArrayList<>(messages);
    this.name = name;
  }

  /**
   * Returns a copy of the list of messages, sorted by date in descending order.
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
}
