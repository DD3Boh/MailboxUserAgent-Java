package mua;

import java.time.ZonedDateTime;

import utils.ASCIICharSequence;
import utils.DateEncoding;

/**
 * Represents a date.
 */
public class Date {
    private ZonedDateTime date;

    /**
     * Constructs a Date object from an ASCIICharSequence.
     *
     * @param asciiCharSequence the ASCIICharSequence to decode the date from
     */
    public Date(ASCIICharSequence asciiCharSequence) {
        this.date = DateEncoding.decode(asciiCharSequence);
    }

    /**
     * Constructs a Date object from three integers representing the year, month, and day.
     * The time is set to midnight in the Europe/Rome timezone.
     *
     * @param year the year
     * @param month the month
     * @param day the day
     */
    public Date(int year, int month, int day) {
        this.date = ZonedDateTime.of(year, month, day, 0, 0, 0, 0, DateEncoding.EUROPE_ROME);
    }

    /**
     * Returns the day of the week of the date.
     *
     * @return the day of the week of the date
     */
    public String getDayOfWeek() {
        return date.getDayOfWeek().toString();
    }

    /**
     * Returns a string representation of the date.
     *
     * @return a string representation of the date
     */
    @Override
    public String toString() {
        return DateEncoding.encode(date).toString();
    }
}
