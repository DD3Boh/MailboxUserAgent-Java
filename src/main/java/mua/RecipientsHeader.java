package mua;

import java.util.List;

/**
 * Represents the recipients header of a message.
 */
public class RecipientsHeader implements Header<List<String>> {
    private List<String> values;

    /**
     * Constructs a RecipientsHeader object with the specified list of email addresses of the recipients.
     *
     * @param values
     */
    public RecipientsHeader(List<String> values) {
        this.values = values;
    }

    /**
     * Returns the list of email addresses of the recipients.
     *
     * @return the list of email addresses of the recipients.
     */
    @Override
    public List<String> getValue() {
        return values;
    }
}

