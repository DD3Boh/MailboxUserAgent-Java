package mua;

import utils.ASCIICharSequence;
import utils.Base64Encoding;

/**
 * Represents an email's header subject, allowing conversion between UTF-8 and ASCII using Base64 encoding.
 */
public class Subject {
    /* the subject text in ASCII */
    private ASCIICharSequence subject;

    /**
     * Creates a new Subject with the given String, converting it to ASCII using Base64 encoding.
     *
     * @param subject the subject text in UTF-8.
     */
    public Subject(String subject) {
        if (ASCIICharSequence.isAscii(subject))
            this.subject = ASCIICharSequence.of(subject);
        else
            this.subject = Base64Encoding.encodeWord(subject);
    }

    /**
     * Creates a new Subject with the given ASCII encoded subject text.
     *
     * @param asciiSubject the ASCII encoded subject text.
     */
    public Subject(ASCIICharSequence asciiSubject) {
        this.subject = asciiSubject;
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
        return subject.toString();
    }
}
