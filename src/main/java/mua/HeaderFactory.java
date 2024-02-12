package mua;

/**
 * A utility factory class for creating Header instances.
 *
 * <p>Each header type is associated with a specific class that implements the Header interface.
 * This factory class provides a method to create a new Header instance based on the given type and
 * value. If the type is not recognized, the method returns null.
 */
public class HeaderFactory {

  /** Private constructor to prevent instantiation of this class. */
  private HeaderFactory() {}

  /**
   * Creates a new Header instance based on the given type and value.
   *
   * @param type the type identifier for the header
   * @param value the value of the header as a string
   * @return a new Header instance corresponding to the type, or null if the type is not recognized
   */
  public static Header<?> createHeader(String type, String value) {
    switch (type.toLowerCase()) {
      case "from":
        return new SenderHeader(Address.fromFullAddress(value));
      case "to":
        return new RecipientsHeader(value);
      case "subject":
        return new SubjectHeader(value);
      case "date":
        return new DateHeader(value);
      case "mime-version":
        return new MimeVersionHeader(value);
      case "content-type":
        return ContentTypeHeader.fromEncodedString(value);
      case "content-transfer-encoding":
        return new ContentTransferEncodingHeader(value);
      case "content-disposition":
        return new ContentDispositionHeader(value);
      default:
        System.out.println("Unknown header type: " + type);
        return null;
    }
  }
}
