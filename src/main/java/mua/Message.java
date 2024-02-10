package mua;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import utils.ASCIICharSequence;
import utils.Fragment;

/** Represents a message composed of multiple message parts. */
public class Message {
  /*
   * Abstraction Function:
   * A Message m represents a sequence of MessageParts mp[1], ..., mp[n] where n is the number of MessageParts.
   * Each MessagePart mp[i] is represented by messageParts.get(i-1) for 1 <= i <= n.
   *
   * Representation Invariant:
   * - messageParts != null
   * - All elements in messageParts are not null
   */
  /** The list of message parts */
  private final List<MessagePart> messageParts;

  /** Constructs an empty message. */
  public Message() {
    messageParts = new ArrayList<>();
  }

  /**
   * Constructs a message with the given List of MessageParts.
   *
   * @param messageParts the list of MessageParts
   * @throws MissingHeaderException if the first part of the message does not contain the From, To,
   *     Subject, and Date headers
   */
  public Message(List<MessagePart> messageParts)
      throws MissingHeaderException, IllegalArgumentException {
    this.messageParts = messageParts;

    MessagePart part = messageParts.get(0);

    if (part == null)
      throw new IllegalArgumentException("The first part of the message cannot be null");

    boolean containsFrom = part.getHeader(SenderHeader.class) != null;
    boolean containsTo = part.getHeader(RecipientsHeader.class) != null;
    boolean containsSubject = part.getHeader(SubjectHeader.class) != null;
    boolean containsDate = part.getHeader(DateHeader.class) != null;

    if (!(containsFrom && containsTo && containsSubject && containsDate))
      throw new MissingHeaderException(
          "The first part of the message must contain the From, To, Subject, and Date headers");
  }

  /**
   * Constructs a list of MessageParts from the given List of Fragments.
   *
   * @param fragments the list of Fragments
   * @return a list of MessageParts
   */
  public static List<MessagePart> createMessageParts(List<Fragment> fragments) {
    List<MessagePart> messageParts = new ArrayList<>();

    for (Fragment fragment : fragments) {
      if (fragment != null) messageParts.add(new MessagePart(fragment));
    }

    return messageParts;
  }

  /**
   * Adds a message part to the message. The part cannot be null.
   *
   * @param part the message part to add
   */
  public void addPart(MessagePart part) {
    messageParts.add(Objects.requireNonNull(part));
  }

  /**
   * Removes a message part from the message.
   *
   * @param part the message part to remove
   * @throws IllegalArgumentException if the specified part is not present in the message
   */
  public void removePart(MessagePart part) {
    if (!messageParts.contains(part))
      throw new IllegalArgumentException("The specified part is not present in the message");

    messageParts.remove(part);
  }

  /**
   * Returns a copy of the list of message parts.
   *
   * @return a copy of the list of message parts
   */
  public List<MessagePart> getParts() {
    return Collections.unmodifiableList(messageParts);
  }

  /**
   * Returns the ASCII representation of the message. The ASCII representation of the message is the
   * concatenation of the ASCII representations of its parts. Each part is separated by a newline
   * character.
   *
   * @return the ASCII representation of the message
   */
  public ASCIICharSequence encodeToASCII() {
    StringBuilder sb = new StringBuilder();

    for (MessagePart part : messageParts) {
      ContentTypeHeader contentHeader = (ContentTypeHeader) part.getHeader(ContentTypeHeader.class);
      sb.append(part.encodeToASCII());
      sb.append("\n");
      if (contentHeader != null && contentHeader.getBoundary() != null) {
        sb.append("--");
        sb.append(contentHeader.getBoundary());
        if (part.equals(messageParts.get(messageParts.size() - 1))) sb.append("--");
        else sb.append("\n");
      }
    }
    return ASCIICharSequence.of(sb.toString());
  }

  /**
   * Returns a string representation of the message.
   *
   * @return a string representation of the message
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    for (MessagePart part : messageParts) {
      ContentTypeHeader contentHeader = (ContentTypeHeader) part.getHeader(ContentTypeHeader.class);
      sb.append(part.toString());
      sb.append("\n");
      if (contentHeader != null && contentHeader.getBoundary() != null) {
        sb.append("--");
        sb.append(contentHeader.getBoundary());
        if (part.equals(messageParts.get(messageParts.size() - 1))) sb.append("--");
        else sb.append("\n");
      }
    }
    return sb.toString();
  }
}
