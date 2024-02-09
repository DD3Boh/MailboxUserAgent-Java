package mua;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Collections;

import utils.*;

/**
 * The MailboxManager class represents a manager for mailboxes.
 * It provides methods to add and remove mailboxes.
 */
public class MailboxManager {
    private final Map<Mailbox, Storage.Box> mailboxMap;
    private final Map<Message, Storage.Box.Entry> messageMap;

    /**
     * Constructs a new MailboxManager object with the given list of mailboxes.
     *
     * @param mailboxes the list of mailboxes
     */
    public MailboxManager(Storage storage) {
        mailboxMap = new LinkedHashMap<>();
        messageMap = new LinkedHashMap<>();

        for (Storage.Box storageBox : storage.boxes()) {
            List<Message> messages = new ArrayList<>();
            for (Storage.Box.Entry entry : storageBox.entries()) {
                ASCIICharSequence sequence = entry.content();
                List<Fragment> fragments = EntryEncoding.decode(sequence);
                Message message = new Message(Message.createMessageParts(fragments));
                messages.add(message);
                messageMap.put(message, entry);
            }
            Mailbox mailbox = new Mailbox(messages, storageBox.toString());
            mailboxMap.put(mailbox, storageBox);
        }
    }

    /**
     * Adds a mailbox to the manager.
     * @param mailbox The mailbox to be added.
     */
    /*public void addMailbox(Mailbox mailbox) {
        mailboxes.add(mailbox);
    }*/

    /**
     * Removes a mailbox from the manager.
     * @param mailbox The mailbox to be removed.
     */
    /*public void removeMailbox(Mailbox mailbox) {
        mailboxes.remove(mailbox);
    }*/

    /**
     * Returns the list of mailboxes.
     * @return the list of mailboxes
     */
    /*public List<Mailbox> getMailboxes() {
        return Collections.unmodifiableList(mailboxes);
    }*/

    /**
     * Deletes a message from the mailbox.
     *
     * @param mailbox the mailbox
     * @param message the message
     */
    public void deleteMessage(Mailbox mailbox, Message message) {
        Storage.Box.Entry entry = messageMap.get(message);
        messageMap.remove(message);
        mailbox.removeMessage(message);
        entry.delete();
    }

    /**
     * Returns the Map of mailboxes and their corresponding Storage.Box.
     *
     * @return the Map of mailboxes and their corresponding Storage.Box
     */
    public Map<Mailbox, Storage.Box> getMailboxMap() {
        return Collections.unmodifiableMap(mailboxMap);
    }

    /**
     * Returns the Map of messages and their corresponding Storage.Box.Entry.
     *
     * @return the Map of messages and their corresponding Storage.Box.Entry
     */
    public Map<Message, Storage.Box.Entry> getMessageMap() {
        return Collections.unmodifiableMap(messageMap);
    }
}
