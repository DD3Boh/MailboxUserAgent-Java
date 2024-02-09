package mua;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import utils.ASCIICharSequence;
import utils.DateEncoding;

/**
 * Represents a date.
 */
public class Date {
    public final ZonedDateTime date;

    /**
     * Constructs a Date object from a ZonedDateTime.
     *
     * @param date the ZonedDateTime to construct the Date object from
     */
    public Date(ZonedDateTime date) {
        this.date = date;
    }

    /**
     * Constructs a Date object from an ASCIICharSequence.
     *
     * @param asciiCharSequence the ASCIICharSequence to decode the date from
     */
    public Date(String dateString) {
        this.date = DateEncoding.decode(ASCIICharSequence.of(dateString));
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
     * Returns the ASCII representation of the date.
     *
     * @return the ASCII representation of the date
     */
    public ASCIICharSequence encodeToASCII() {
        return DateEncoding.encode(date);
    }

    /**
     * Returns a string representation of the date.
     *
     * @return a string representation of the date
     */
    @Override
    public String toString() {
        return date.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
}
