package mua;

import java.util.ArrayList;
import java.util.List;

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
     * Constructs a message with the given message parts.
     *
     * @param messageParts the list of message parts
     */
    public Message(List<MessagePart> messageParts) {
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
}
