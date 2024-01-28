package mua;

/**
 * Represents the date header of when the message was composed.
 */
public class DateHeader implements Header<Date> {
    private Date value;

    /**
     * Constructs a DateHeader object with the specified date and time when the message was composed.
     *
     * @param value
     */
    public DateHeader(Date value) {
        this.value = value;
    }

    /**
     * Returns a string representing the type of the header.
     *
     * @return a string representing the type of the header
     */
    @Override
    public String getType() {
        return "Date";
    }

    /**
     * Returns the date and time when the message was composed.
     *
     * @return the date and time when the message was composed.
     */
    @Override
    public Date getValue() {
        return value;
    }

    /**
     * Returns a string representation of the DateHeader object.
     *
     * @return a string representation of the DateHeader object
     */
    @Override
    public String toString() {
        return getType() + ": " + value.toString();
    }
}
