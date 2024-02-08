package mua;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

/**
 * Represents a mailbox that stores messages.
 */
public class Mailbox {
    public final String name;
    private final List<Message> messages;

    /**
     * Constructs a Mailbox object with the given list of messages.
     * 
     * @param messages the list of messages to be stored in the mailbox
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
        Collections.sort(sortedMessages, new Comparator<Message>() {
            @Override
            public int compare(Message m1, Message m2) {
                Date date1 = (Date) m1.getParts().get(0).getHeader(DateHeader.class).getValue();
                Date date2 = (Date) m2.getParts().get(0).getHeader(DateHeader.class).getValue();
                return date2.date.compareTo(date1.date);
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
