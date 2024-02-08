package mua;

import java.util.List;
import java.util.ArrayList;

import utils.ASCIICharSequence;
import utils.Base64Encoding;
import utils.Fragment;
import java.util.Collections;
import java.util.Comparator;

public class MessagePart {
    private final List<Header<?>> headers;
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
     * @param body    the body
     */
    public MessagePart(List<Header<?>> headers, String body) {
        this.headers = new ArrayList<>();

        for (Header<?> header : headers)
            this.headers.add(header);

        reorderHeaders();

        ContentTransferEncodingHeader contentEncodingHeader = (ContentTransferEncodingHeader) getHeader(ContentTransferEncodingHeader.class);
        if (contentEncodingHeader != null)
            this.body = Base64Encoding.encode(body);
        else
            this.body = ASCIICharSequence.of(body);
    }

    public void addHeader(Header<?> header) {
        this.headers.add(header);
        reorderHeaders();
    }

    /**
     * Reorder the headers of the message part.
     */
    private void reorderHeaders() {
        List<String> orderList = new ArrayList<>(List.of("From", "To", "Subject", "Date", "MIME-Version", "Content-Type", "Content-Transfer-Encoding"));

        Comparator<Header<?>> comparator = new Comparator<Header<?>>() {
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
     * Find the istance of the header with the specified Header type.
     * If the header is not found, null is returned.
     *
     * @param headerType the type of the header.
     * @return the header with the specified type, or null if not found.
     */
    public Header<?> getHeader(Class<?> headerClass) {
        for (Header<?> header : headers) {
            if (headerClass.isInstance(header))
                return header;
        }
        return null;
    }

    /**
     * Parse the header of the message part, given the raw header.
     *
     * @param fragment the Fragment
     */
    private Header<?> parseHeader(List<ASCIICharSequence> rawHeader) {
        String headerName = rawHeader.get(0).toString();
        String headerValue = rawHeader.get(1).toString();

        return HeaderFactory.createHeader(headerName, headerValue);
    }

    /**
     * Returns the header of the message part.
     *
     * @return the header
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
     * Returns a String representation of the message part.
     *
     * @return a String representation of the message part
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Header<?> header : headers) {
            sb.append(header.toString());
            sb.append("\n");
        }
        sb.append("\n");
        sb.append(body.toString());
        return sb.toString();
    }
}
