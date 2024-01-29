package mua;

import java.util.ArrayList;
import java.util.List;

import utils.Fragment;

/**
 * Represents a message composed of multiple message parts.
 */
public class Message {
    private List<MessagePart> messageParts;

    /**
     * Constructs an empty message.
     */
    public Message() {
        messageParts = new ArrayList<>();
    }

    /**
     * Constructs a message with the given List of Fragments.
     *
     * @param fragments the list of Fragments
     */
    public Message(List<Fragment> fragments) {
        messageParts = new ArrayList<>();

        for (Fragment fragment : fragments) {
            messageParts.add(new MessagePart(fragment));
        }
    }

    /**
     * Constructs a message with the given List of MessageParts.
     *
     * @param messageParts the list of MessageParts
     */
    public Message(List<MessagePart> messageParts, boolean dummy) {
        this.messageParts = messageParts;
    }

    /**
     * Adds a message part to the message.
     *
     * @param part the message part to add
     */
    public void addPart(MessagePart part) {
        messageParts.add(part);
    }

    /**
     * Removes a message part from the message.
     *
     * @param part the message part to remove
     */
    public void removePart(MessagePart part) {
        messageParts.remove(part);
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
