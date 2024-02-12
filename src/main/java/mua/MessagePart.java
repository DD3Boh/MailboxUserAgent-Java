package mua;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import utils.ASCIICharSequence;
import utils.Base64Encoding;
import utils.Fragment;

/** Represents a message part of a message. */
public final class MessagePart {
  /*
   * Abstraction Function:
   * Represents a message part of a message. An instance of MessagePart represents a message part with the following parts:
   * - headers: the list of headers of the message part
   * - body: the body of the message part
   * The body of the message part is saved as an encoded ASCIICharSequence if the Content-Transfer-Encoding header is set to "base64".
   * The ASCII representation of the message part is the concatenation of the ASCII representations of its headers,
   * followed by a newline character, and the body.
   *
   * Representation Invariant:
   * - headers is not null and does not contain null elements.
   * - headers cannot be empty.
   * - body is not null or empty.
   */

  /** The list of headers of the message part */
  private final List<Header<?>> headers;

  /** The body of the message part */
  public final ASCIICharSequence body;

  /**
   * Construct a MessagePart object with the specified headers and body. The headers of the message
   * part are reordered according to the order list. The body of the message part is encoded with
   * Base64 if the Content-Transfer-Encoding header is set to "base64". The body of the message part
   * is the raw body if the Content-Transfer-Encoding header is not set. The body that is passed as
   * "body" needs to necessarily be already decoded into a normal String, and not encoded as base64.
   *
   * @param headers the headers of the message part
   * @param body the body of the message part
   * @throws IllegalArgumentException if one of headers is null
   */
  public MessagePart(List<Header<?>> headers, String body) throws IllegalArgumentException {
    if (headers.contains(null)) throw new IllegalArgumentException("The headers cannot be null");

    this.headers = new ArrayList<>(headers);
    reorderHeaders();

    ContentTransferEncodingHeader contentEncodingHeader =
        (ContentTransferEncodingHeader) getHeader(ContentTransferEncodingHeader.class);

    if (contentEncodingHeader != null && contentEncodingHeader.getValue().equals("base64"))
      this.body = Base64Encoding.encode(body);
    else this.body = ASCIICharSequence.of(body);
  }

  /**
   * Static constructor of a MessagePart object with the specified Fragment.
   *
   * <p>The Fragment must contain at least one header and a body. The headers of the message part
   * are parsed from the raw headers of the Fragment. The body of the message part is the raw body
   * of the Fragment.
   *
   * @param fragment the Fragment
   * @throws IllegalArgumentException if the fragment is null
   * @throws IllegalArgumentException if the fragment does not contain at least one header
   * @throws IllegalArgumentException if the fragment does not contain a body
   * @return the MessagePart object
   */
  public static MessagePart fromFragment(Fragment fragment) {
    if (fragment == null) throw new IllegalArgumentException("The fragment cannot be null");
    if (fragment.rawHeaders().isEmpty())
      throw new IllegalArgumentException("The fragment must contain at least one header");
    if (fragment.rawBody().isEmpty())
      throw new IllegalArgumentException("The fragment must contain a body");

    List<Header<?>> headers = new ArrayList<>();
    String body = fragment.rawBody().toString();

    for (List<ASCIICharSequence> rawHeader : fragment.rawHeaders()) {
      Header<?> header = parseHeader(rawHeader);
      headers.add(header);
      if (header.getType().equals("Content-Transfer-Encoding"))
        if (header.getValue().equals("base64")) body = Base64Encoding.decode(fragment.rawBody());
    }

    return new MessagePart(headers, body);
  }

  /**
   * Reorder the headers of the message part according to the order list. The order list is the list
   * of headers in the order they should appear in the message part. If a header is not in the order
   * list, it is placed at the end of the list.
   */
  private void reorderHeaders() {
    List<String> orderList =
        new ArrayList<>(
            List.of(
                "From",
                "To",
                "Subject",
                "Date",
                "MIME-Version",
                "Content-Type",
                "Content-Transfer-Encoding",
                "Content-Disposition"));

    Comparator<Header<?>> comparator =
        new Comparator<Header<?>>() {
          @Override
          public int compare(Header<?> h1, Header<?> h2) {
            int index1 = orderList.indexOf(h1.getType());
            int index2 = orderList.indexOf(h2.getType());

            if (index1 == -1) index1 = Integer.MAX_VALUE;
            if (index2 == -1) index2 = Integer.MAX_VALUE;

            return Integer.compare(index1, index2);
          }
        };

    Collections.sort(headers, comparator);
  }

  /**
   * Find the istance of the header with the specified Header type. If the header is not found, null
   * is returned.
   *
   * @param headerClass the class of the header to find
   * @return the header with the specified type, or null if not found.
   */
  public Header<?> getHeader(Class<?> headerClass) {
    for (Header<?> header : headers) if (headerClass.isInstance(header)) return header;

    return null;
  }

  /**
   * Static parsing for the header of the message part, given the raw header, as a list of
   * ASCIICharSequence.
   *
   * @param rawHeader the raw header
   * @return the parsed Header object.
   * @throws IllegalArgumentException if the raw header does not contain two elements
   * @throws IllegalArgumentException if the raw header contains null elements
   */
  private static Header<?> parseHeader(List<ASCIICharSequence> rawHeader) {
    if (rawHeader.size() != 2)
      throw new IllegalArgumentException("The raw header must contain two elements");

    String headerName = rawHeader.get(0).toString();
    String headerValue = rawHeader.get(1).toString();

    if (headerName == null || headerValue == null)
      throw new IllegalArgumentException("The raw header cannot contain null elements");

    return HeaderFactory.createHeader(headerName, headerValue);
  }

  /**
   * Returns a copy of the headers of the message part.
   *
   * @return the list of headers of the message part.
   */
  public List<Header<?>> getHeaders() {
    return new ArrayList<>(headers);
  }

  /**
   * Returns the ASCII representation of the message part. The ASCII representation of the message
   * part is the concatenation of the ASCII representations of its headers, followed by a newline
   * character, and the ascii representation of the body.
   *
   * @return the ASCII representation of the message part
   */
  public ASCIICharSequence encodeToASCII() {
    StringBuilder sb = new StringBuilder();
    for (Header<?> header : headers) {
      sb.append(header.encodeToASCII());
      sb.append("\n");
    }
    sb.append("\n");
    sb.append(body.toString());
    return ASCIICharSequence.of(sb.toString());
  }

  /**
   * Returns the decoded body of the message part. If the body is not encoded, the original body is
   * returned.
   *
   * @return the decoded body of the message part
   */
  public String getBodyDecoded() {
    ContentTransferEncodingHeader contentEncodingHeader =
        (ContentTransferEncodingHeader) getHeader(ContentTransferEncodingHeader.class);

    if (contentEncodingHeader != null && contentEncodingHeader.getValue().equals("base64")) 
      return Base64Encoding.decode(this.body);

    return this.body.toString();
  }
}
