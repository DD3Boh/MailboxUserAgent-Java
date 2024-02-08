package mua;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Represents a mailbox that stores messages.
 */
public class Mailbox implements Iterable<Message> {
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

    /**
     * Returns an iterator over the messages in the mailbox.
     * The messages are sorted by date in descending order.
     *
     * @return an iterator over the messages in the mailbox
     */
    @Override
    public Iterator<Message> iterator() {
        return new Iterator<Message>() {
            private int currentIndex = 0;
            private List<Message> sortedMessages;

            {
                sortedMessages = new ArrayList<>(messages);
                Collections.sort(sortedMessages, new Comparator<Message>() {
                    @Override
                    public int compare(Message m1, Message m2) {
                        Date date1 = (Date) m1.getParts().get(0).getHeader(DateHeader.class).getValue();
                        Date date2 = (Date) m2.getParts().get(0).getHeader(DateHeader.class).getValue();
                        return date2.date.compareTo(date1.date);
                    }
                });
            }

            @Override
            public boolean hasNext() {
                return currentIndex < sortedMessages.size();
            }

            @Override
            public Message next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return sortedMessages.get(currentIndex++);
            }
        };
    }
}
