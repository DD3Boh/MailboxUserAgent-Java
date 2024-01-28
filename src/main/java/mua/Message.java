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
