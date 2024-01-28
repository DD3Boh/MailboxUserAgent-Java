package mua;

/**
 * Represents a MIME-Version header.
 */
public class MimeVersionHeader implements Header<Double> {
    private final Double value;

    /**
     * Constructs a new MIME-Version header with the specified version.
     *
     * @param value the MIME version
     */
    public MimeVersionHeader(Double value) {
        this.value = value;
    }

    /**
     * Returns the type of the header.
     *
     * @return the type of the header, "MIME-Version"
     */
    @Override
    public String getType() {
        return "MIME-Version";
    }

    /**
     * Returns the value of the MIME-Version header.
     *
     * @return the MIME version value
     */
    @Override
    public Double getValue() {
        return value;
    }

    /**
     * Returns a string representation of the MIME-Version header.
     *
     * @return a string representation of the MIME-Version header
     */
    @Override
    public String toString() {
        return getType() + ": " + getValue();
    }
}
