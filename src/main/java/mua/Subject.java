package mua;

import utils.ASCIICharSequence;
import utils.Base64Encoding;

/**
 * Represents an email's header subject, allowing conversion between UTF-8 and ASCII using Base64 encoding.
 */
public class Subject {
    /* the subject text in UTF-8 */
    private String subject;

    /**
     * Creates a new Subject with the given text.
     *
     * @param subject the subject text in UTF-8.
     */
    public Subject(String subject) {
        this.subject = subject;
    }

    /**
     * Creates a new Subject with the given ASCII encoded subject text.
     *
     * @param asciiSubject the ASCII encoded subject text.
     */
    public Subject(ASCIICharSequence asciiSubject) {
        this.subject = decodeFromAscii(asciiSubject);
    }

    /**
     * Encodes the UTF-8 subject text into an ASCII string using Base64 encoding.
     *
     * @return the ASCII encoded subject text.
     */
    public ASCIICharSequence encodeToAscii() {
        if (ASCIICharSequence.isAscii(subject))
            return ASCIICharSequence.of(subject);
        else
            return Base64Encoding.encodeWord(subject);
    }

    /**
     * Decodes an ASCII encoded subject text back into a UTF-8 string.
     * If the given ASCII string is not a valid Base64 encoding, the original string is returned.
     *
     * @param asciiSubject the ASCII encoded subject text.
     * @return the UTF-8 decoded subject text.
     */
    public static String decodeFromAscii(ASCIICharSequence asciiSubject) {
        String word = Base64Encoding.decodeWord(asciiSubject);

        if (word == null)
            word = asciiSubject.toString();

        return word;
    }

    /**
     * Returns a string representation of the Subject object.
     *
     * @return a string representation of the Subject object.
     */
    @Override
    public String toString() {
        return subject;
    }
}