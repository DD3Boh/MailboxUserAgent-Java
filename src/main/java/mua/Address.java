package mua;

import java.util.List;

import utils.ASCIICharSequence;
import utils.AddressEncoding;

/**
 * Represents an email address with display name, local part, and domain.
 */
public class Address {
    /* the display name associated with the email address */
    private final String displayName;
    /* the local part of the email address (before the '@') */
    private final String local;
    /* domain the domain part of the email address (after the '@') */
    private final String domain;

    /**
     * Constructs a new Address instance from a full email address string that may include a display name.
     *
     * The expected format is "Display Name <local@domain>". If the display name is not present,
     * the format should be "local@domain".
     *
     * @param fullAddress the full email address string
     * @throws IllegalArgumentException if the email address is invalid, according to exceptions thrown by the helper methods
     */
    public Address(String fullAddress) throws IllegalArgumentException {
        ASCIICharSequence ascii = ASCIICharSequence.of(fullAddress);
        List<List<String>> addresses = AddressEncoding.decode(ascii);

        if (addresses.size() != 1 || addresses.get(0).size() != 3)
            throw new IllegalArgumentException("Invalid email address");

        if (!AddressEncoding.isValidAddressPart(addresses.get(0).get(1)))
            throw new IllegalArgumentException("Invalid local part");

        if (!AddressEncoding.isValidAddressPart(addresses.get(0).get(2)))
            throw new IllegalArgumentException("Invalid domain part");

        this.displayName = addresses.get(0).get(0);
        this.local = addresses.get(0).get(1);
        this.domain = addresses.get(0).get(2);
    }

    /**
     * Constructs a new Address instance from a display name, local part, and domain.
     *
     * @param displayName the display name
     * @param local the local part
     * @param domain the domain part
     */
    public Address(String displayName, String local, String domain) {
        if (!AddressEncoding.isValidAddressPart(local))
            throw new IllegalArgumentException("Invalid local part");

        if (!AddressEncoding.isValidAddressPart(domain))
            throw new IllegalArgumentException("Invalid domain part");

        this.displayName = displayName;
        this.local = local;
        this.domain = domain;
    }

    /**
     * Returns the full email address as a string, including the display name if present.
     *
     * @return the full email address.
     */
    public String getFullAddress() {
        if (!displayName.isEmpty()) {
            return String.format("%s <%s@%s>", displayName, local, domain);
        }
        return String.format("%s@%s", local, domain);
    }

    /**
     * Returns the display name associated with the email address.
     *
     * @return the display name.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Returns the local part of the email address.
     *
     * @return the local part.
     */
    public String getLocal() {
        return local;
    }

    /**
     * Returns the domain part of the email address.
     *
     * @return the domain part.
     */
    public String getDomain() {
        return domain;
    }
}
