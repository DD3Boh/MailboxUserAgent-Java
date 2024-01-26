package mua;

/**
 * Represents a generic interface for message headers.
 */
public interface Header<T> {
    /**
     * Returns the value of the header.
     *
     * @return the value of the header
     */
    T getValue();
}
