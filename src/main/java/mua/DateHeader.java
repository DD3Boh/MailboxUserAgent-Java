package mua;

import utils.ASCIICharSequence;
import utils.DateEncoding;

import java.time.ZonedDateTime;

/**
 * Represents the date header of when the message was composed.
 */
public class DateHeader implements Header<ZonedDateTime> {
    /** The value of the Date header, represented as a ZonedDateTime object */
    private ZonedDateTime value;

    /**
     * Constructs a DateHeader object with the specified date and time when the message was composed.
     *
     * @param value a string in format RFC_1123_DATE_TIME representing the date and time when the message was composed.
     */
    public DateHeader(String value) {
        this.value = DateEncoding.decode(ASCIICharSequence.of(value));
    }

    /**
     * Constructs a DateHeader object with the specified ZonedDateTime object.
     *
     * @param value the date and time when the message was composed, represented as a ZonedDateTime object.
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

    /**
     * Returns an ASCII representation of the DateHeader object.
     *
     * @return an ASCII representation of the DateHeader object
     */
    @Override
    public ASCIICharSequence encodeToASCII() {
        return ASCIICharSequence.of(getType() + ": " + DateEncoding.encode(value));
    }

    /**
     * Returns a string representation of the DateHeader object.
     *
     * @return a string representation of the DateHeader object
     */
    @Override
    public String toString() {
        return value.toString();
    }
}
