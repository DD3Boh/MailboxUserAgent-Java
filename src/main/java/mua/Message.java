package mua;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import utils.Fragment;
import java.util.Collections;

/**
 * Represents a message composed of multiple message parts.
 */
public class Message {
    /**
     * Abstraction Function:
     * A Message m represents a sequence of MessageParts mp[1], ..., mp[n] where n is the number of MessageParts.
     * Each MessagePart mp[i] is represented by messageParts.get(i-1) for 1 <= i <= n.
     *
     * Representation Invariant:
     * - messageParts != null
     * - All elements in messageParts are not null
     */
    private final List<MessagePart> messageParts;

    /**
     * Constructs an empty message.
     */
    public Message() {
        messageParts = new ArrayList<>();
    }

    /**
     * Constructs a message with the given List of MessageParts.
     *
     * @param messageParts the list of MessageParts
     */
    public Message(List<MessagePart> messageParts) {
        this.messageParts = messageParts;
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
            if (fragment != null)
                messageParts.add(new MessagePart(fragment));
        }

        return messageParts;
    }

    /**
     * Adds a message part to the message.
     * The part cannot be null.
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
     * Encodes the messsage into a String.
     *
     * @return the encoded message
     */
    public String encodeMessage() {
        StringBuilder sb = new StringBuilder();
        for (MessagePart part : messageParts) {
            if (part.equals(messageParts.get(messageParts.size() - 1)))
                sb.append(part.encodeMessagePart(true));
            else
                sb.append(part.encodeMessagePart(false) + "\n");
        }
        return sb.toString();
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
            sb.append(part.toString());
            sb.append("\n");
        }
        return sb.toString();
    }
}
