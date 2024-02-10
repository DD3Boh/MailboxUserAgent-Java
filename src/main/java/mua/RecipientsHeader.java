package mua;

import utils.ASCIICharSequence;

/**
 * Represents the recipients header of a message, using a Recipients object.
 */
public class RecipientsHeader implements Header<Recipients> {
    /** The value of the Recipients header, represented as a Recipients object */
    private Recipients recipients;

    /**
     * Constructs a RecipientsHeader object with the specified Recipients objects.
     *
     * @param recipients the Recipients object of the recipients.
     */
    public RecipientsHeader(Recipients recipients) {
        this.recipients = recipients;
    }

    /**
     * Returns a string representing the type of the header.
     *
     * @return a string representing the type of the header
     */
    @Override
    public String getType() {
        return "To";
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

    /**
     * Returns an ASCII representation of the RecipientsHeader object.
     *
     * @return an ASCII representation of the RecipientsHeader object
     */
    @Override
    public ASCIICharSequence encodeToASCII() {
        return ASCIICharSequence.of(getType() + ": " + recipients.encodeToASCII());
    }

    /**
     * Returns a string representation of the RecipientsHeader object.
     *
     * @return a string representation of the RecipientsHeader object
     */
    @Override
    public String toString() {
        return recipients.toString();
    }
}
