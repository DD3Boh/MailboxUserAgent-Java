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
        switch (type.toLowerCase()) {
            case "from":
                return new SenderHeader(new Address(value));
            case "to":
                return new RecipientsHeader(new Recipients(value));
            case "subject":
                return new SubjectHeader(new Subject(ASCIICharSequence.of(value)));
            case "date":
                return new DateHeader(new Date(ASCIICharSequence.of(value)));
            case "mime-version":
                return new MimeVersionHeader(Double.parseDouble(value));
            case "content-type":
                return new ContentTypeHeader(value);
            case "content-transfer-encoding":
                return new ContentTransferEncodingHeader(value);
            default:
                System.out.println("Unknown header type: " + type);
                return null;
        }
    }
}