package mua;

/**
 * Represents the recipients header of a message, using a Recipients object.
 */
public class RecipientsHeader implements Header<Recipients> {
    private Recipients recipients;

    /**
     * Constructs a RecipientsHeader object with the specified Recipients objects.
     *
     * @param values the Recipients object of the recipients
     */
    public RecipientsHeader(Recipients recipients) {
        this.recipients = recipients;
    }

    /**
     * Returns the Recipients object for the recipients.
     *
     * @return the Recipients object for the recipients.
     */
    @Override
    public Recipients getValue() {
        return recipients;
    }
}
