package mua;

import utils.ASCIICharSequence;

public class HeaderFactory {

    /**
     * Creates a new Header instance based on the given type and value.
     *
     * @param type  the type identifier for the header
     * @param value the value of the header as a string
     * @return a new Header instance corresponding to the type, or null if the type is not recognized
     */
    public static Header<?> createHeader(String type, String value) {
        switch (type) {
            case "From":
                return new SenderHeader(new Address(value));
            case "To":
                return new RecipientsHeader(new Recipients(value));
            case "Subject":
                return new SubjectHeader(new Subject(ASCIICharSequence.of(value)));
            case "Date":
                return new DateHeader(new Date(ASCIICharSequence.of(value)));
            case "MIME-Version":
                return new MimeVersionHeader(Double.parseDouble(value));
            case "Content-Type":
                return new ContentTypeHeader(value);
            case "Content-Transfer-Encoding":
                return new ContentTransferEncodingHeader(value);
            default:
                System.out.println("Unknown header type: " + type);
                return null;
        }
    }
}
