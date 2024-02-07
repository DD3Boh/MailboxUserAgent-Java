package mua;

import java.util.List;

/**
 * Represents a mailbox that stores messages.
 */
public class Mailbox {
    private final List<Message> messages;

    /**
     * Constructs a Mailbox object with the given list of messages.
     * 
     * @param messages the list of messages to be stored in the mailbox
     */
    public Mailbox(List<Message> messages) {
        this.messages = messages;
    }

    /**
     * Returns the list of messages stored in the mailbox.
     * 
     * @return the list of messages
     */
    public List<Message> getMessages() {
        return messages;
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
