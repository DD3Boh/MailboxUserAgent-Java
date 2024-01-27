package mua;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a collection of email recipients.
 * Implements the Iterable interface to enable iteration over the recipients.
 */
public class Recipients implements Iterable<Address> {
    private final List<Address> addresses;

    /**
     * Constructs an empty Recipients object.
     *
     * The addresses list is initialized as an empty ArrayList.
     */
    public Recipients() {
        this.addresses = new ArrayList<>();
    }

    /**
     * Constructs a Recipients object from a string containing a list of addresses.
     *
     * @param addressList The string containing a list of addresses.
     */
    public Recipients(String addressList) {
        this.addresses = new ArrayList<>();

        if (addressList.startsWith("To: "))
            addressList = addressList.substring("To: ".length());

        String[] addressArray = addressList.split(", ");
        for (String addressString : addressArray) {
            Address address = new Address(addressString);
            this.addresses.add(address);
        }
    }

    /**
     * Adds an address to the recipients list.
     *
     * @param address The address to be added.
     */
    public void addAddress(Address address) {
        this.addresses.add(address);
    }

    /**
     * Removes an address from the recipients list.
     *
     * @param address The address to be removed.
     */
    public void removeAddress(Address address) {
        this.addresses.remove(address);
    }

    /**
     * Returns an iterator over the recipients list.
     *
     * @return An iterator over the recipients list.
     */
    @Override
    public Iterator<Address> iterator() {
        return addresses.iterator();
    }
}
