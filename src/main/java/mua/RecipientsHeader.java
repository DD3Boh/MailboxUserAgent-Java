package mua;

import java.util.List;

/**
 * Represents the recipients header of a message, using a list of Address objects.
 */
public class RecipientsHeader implements Header<List<Address>> {
    private List<Address> values;

    /**
     * Constructs a RecipientsHeader object with the specified list of Address objects.
     *
     * @param values the list of Address objects of the recipients
     */
    public RecipientsHeader(List<Address> values) {
        this.values = values;
    }

    /**
     * Returns the list of Address objects for the recipients.
     *
     * @return the list of Address objects for the recipients.
     */
    @Override
    public List<Address> getValue() {
        return values;
    }
}
