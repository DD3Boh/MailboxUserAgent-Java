package mua;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import utils.ASCIICharSequence;

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
            addAddress(address);
        }
    }

    /**
     * Adds an address starting from its decoded string representation.
     * The string representation is split into display name, local part, and domain.
     * The variables are separated by commas.
     * The display name may be empty.
     *
     * @param addressString The string representation of the address.
     */
    public void addAddress(String addressString) {
        String addressArray[] = addressString.split(",");
        String displayName = addressArray[0].trim();
        String local = addressArray[1].trim();
        String domain = addressArray[2].trim();

        Address address = new Address(displayName, local, domain);
        addAddress(address);
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

    /**
     * Returns the ASCII representation of the Recipients object.
     * The ASCII representation of the Recipients object is the concatenation of the ASCII representations of its addresses.
     * Each address is separated by a comma and a space.
     *
     * @return the ASCII representation of the Recipients object
     */
    public ASCIICharSequence encodeToASCII() {
        StringBuilder sb = new StringBuilder();
        Iterator<Address> iterator = addresses.iterator();

        while (iterator.hasNext()) {
            Address address = iterator.next();

            sb.append(address);

            if (iterator.hasNext())
                sb.append(", ");
        }

        return ASCIICharSequence.of(sb.toString());
    }

    /**
     * Returns a string representation of the Recipients object.
     * The string includes all the addresses in the Recipients object, separated by commas.
     *
     * @return a string representation of the Recipients object
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Iterator<Address> iterator = addresses.iterator();

        while (iterator.hasNext()) {
            Address address = iterator.next();

            sb.append(address);

            if (iterator.hasNext())
                sb.append(", ");
        }

        return sb.toString();
    }
}
