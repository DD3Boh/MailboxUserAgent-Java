package mua;

import java.time.ZonedDateTime;

/**
 * Represents the date header of when the message was composed.
 */
public class DateHeader implements Header<ZonedDateTime> {
    private ZonedDateTime value;

    /**
     * Constructs a DateHeader object with the specified date and time when the message was composed.
     *
     * @param value
     */
    public DateHeader(ZonedDateTime value) {
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
    public ZonedDateTime getValue() {
        return value;
    }
}
