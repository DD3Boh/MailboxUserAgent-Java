package mua;

/**
 * Represents the sender header of a message, using an Address object.
 */
public class SenderHeader implements Header<Address> {
    private Address value;

    /**
     * Constructs a SenderHeader object with the specified Address object.
     *
     * @param value the Address object of the sender
     */
    public SenderHeader(Address value) {
        this.value = value;
    }

    /**
     * Returns a string representing the type of the header.
     *
     * @return a string representing the type of the header
     */
    @Override
    public String getType() {
        return "From";
    }

    /**
     * Returns the Address object of the sender.
     *
     * @return the Address object of the sender.
     */
    @Override
    public Address getValue() {
        return value;
    }
}
