package mua;

import java.util.ArrayList;
import java.util.List;
import utils.ASCIICharSequence;
import utils.Fragment;

/** Represents a message composed of multiple message parts. */
public final class Message {
  /*
   * Abstraction Function:
   * Represents a message composed of multiple message parts. An instance of Message represents a
   * message with the following parts: messageParts.get(0), messageParts.get(1), ..., messageParts.get(n).
   * Each message needs to contain at least one part.
   * The first part of the message must contain the From, To, Subject, and Date headers.
   * The ASCII representation of the message is the concatenation of the ASCII representations of its parts,
   * separated by a newline character.
   *
   * Representation Invariant:
   * - messageParts is not null and does not contain null elements.
   * - messageParts is not empty.
   * - The first part of the message must contain the From, To, Subject, and Date headers.
   * - The ASCII representation of the message is the concatenation of the ASCII representations of its parts,
   *   separated by a newline character.
   */

  /** The list of message parts */
  private final List<MessagePart> messageParts;

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

    if (messageParts == null) throw new IllegalArgumentException("The message parts cannot be null");
    if (messageParts.isEmpty()) throw new IllegalArgumentException("The message must contain at least one part");

    MessagePart part = messageParts.get(0);

    if (part == null) throw new IllegalArgumentException("The first part of the message cannot be null");

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
   * @throws IllegalArgumentException if the fragments are null or empty
   */
  public static List<MessagePart> createMessageParts(List<Fragment> fragments) throws IllegalArgumentException {
    List<MessagePart> messageParts = new ArrayList<>();

    if (fragments == null) throw new IllegalArgumentException("The fragments cannot be null");
    if (fragments.isEmpty()) throw new IllegalArgumentException("The message must contain at least one part");

    for (Fragment fragment : fragments)
      if (fragment != null) messageParts.add(MessagePart.fromFragment(fragment));

    return messageParts;
  }

  /**
   * Returns a copy of the list of message parts.
   *
   * @return a copy of the list of message parts
   */
  public List<MessagePart> getParts() {
    return new ArrayList<>(messageParts);
  }

  /**
   * Returns the ASCII representation of the message.
   *
   * The ASCII representation of the message is the concatenation of the ASCII representations of its parts.
   * Each part is separated by a newline character.
   *
   * @return the ASCII representation of the message.
   */
  public ASCIICharSequence encodeToASCII() {
    StringBuilder sb = new StringBuilder();

    MessagePart firstPart = messageParts.get(0);
    for (MessagePart part : messageParts) {
      ContentTypeHeader contentHeader = (ContentTypeHeader) firstPart.getHeader(ContentTypeHeader.class);
      sb.append(part.encodeToASCII());
      sb.append("\n");
      if (contentHeader != null && contentHeader.getBoundary() != "") {
        sb.append("--");
        sb.append(contentHeader.getBoundary());
        if (part.equals(messageParts.get(messageParts.size() - 1))) sb.append("--");
        else sb.append("\n");
      }
    }
    return ASCIICharSequence.of(sb.toString());
  }
}
