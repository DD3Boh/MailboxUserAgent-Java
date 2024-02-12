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
  /** The list of headers of the message part */
  private final List<Header<?>> headers;

  /** The body of the message part */
  public final ASCIICharSequence body;

  /**
   * Construct a MessagePart object with the specified Fragment.
   *
   * @param fragment the Fragment
   */
  public MessagePart(Fragment fragment) {
    this.headers = new ArrayList<>();
    for (List<ASCIICharSequence> rawHeader : fragment.rawHeaders())
      this.headers.add(parseHeader(rawHeader));
    this.body = fragment.rawBody();
    reorderHeaders();
  }

  /**
   * Construct a MessagePart object with the specified headers and body.
   *
   * @param headers the headers
   * @param body the body
   */
  public MessagePart(List<Header<?>> headers, String body) {
    this.headers = new ArrayList<>();

    for (Header<?> header : headers) this.headers.add(header);

    reorderHeaders();

    ContentTransferEncodingHeader contentEncodingHeader =
        (ContentTransferEncodingHeader) getHeader(ContentTransferEncodingHeader.class);
    if (contentEncodingHeader != null) this.body = Base64Encoding.encode(body);
    else this.body = ASCIICharSequence.of(body);
  }

  /** Reorder the headers of the message part. */
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
                "Content-Disposition"
            ));

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
    for (Header<?> header : headers) {
      if (headerClass.isInstance(header)) return header;
    }
    return null;
  }

  /**
   * Parse the header of the message part, given the raw header.
   *
   * @param rawHeader the raw header
   * @return the parsed Header object.
   */
  private Header<?> parseHeader(List<ASCIICharSequence> rawHeader) {
    String headerName = rawHeader.get(0).toString();
    String headerValue = rawHeader.get(1).toString();

    return HeaderFactory.createHeader(headerName, headerValue);
  }

  /**
   * Returns the headers of the message part.
   *
   * @return the list of headers of the message part
   */
  public List<Header<?>> getHeaders() {
    return Collections.unmodifiableList(headers);
  }

  /**
   * Returns the ASCII representation of the message part.
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
    if (contentEncodingHeader == null) return this.body.toString();

    return Base64Encoding.decode(this.body);
  }

  /**
   * Merge two message parts into a single message part. The merged message part has the headers of
   * both message parts and the body of the second message part. If a header is present in both
   * message parts, the header of the second message part is used.
   *
   * @param messagePart1 the first message part
   * @param messagePart2 the second message part
   * @return the merged message part
   */
  public static MessagePart mergeMessageParts(MessagePart messagePart1, MessagePart messagePart2) {
    List<Header<?>> mergedHeaders = new ArrayList<>(messagePart1.getHeaders());

    for (Header<?> header : messagePart2.getHeaders()) {
      Header<?> existingHeader = messagePart1.getHeader(header.getClass());
      if (existingHeader != null) mergedHeaders.remove(existingHeader);
      mergedHeaders.add(header);
    }

    String mergedBody = messagePart2.getBodyDecoded();

    return new MessagePart(mergedHeaders, mergedBody);
  }
}
