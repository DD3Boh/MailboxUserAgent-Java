package mua;

import java.util.List;
import java.util.ArrayList;

import utils.ASCIICharSequence;
import utils.Base64Encoding;
import utils.Fragment;
import java.util.Collections;

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

        ContentTransferEncodingHeader contentEncodingHeader = (ContentTransferEncodingHeader) getHeader(ContentTransferEncodingHeader.class);
        if (contentEncodingHeader != null)
            this.body = Base64Encoding.encode(body);
        else
            this.body = ASCIICharSequence.of(body);
    }

    public void addHeader(Header<?> header) {
        this.headers.add(header);
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
     * Decodes an ASCII encoded sequence text back into a UTF-8 string.
     * If the given ASCII string is not a valid Base64 encoding, the original string is returned.
     *
     * @param asciiSubject the ASCII encoded subject text.
     * @return the UTF-8 decoded subject text.
     */
    public static String decodeFromAscii(ASCIICharSequence asciiSequence) {
        String word = Base64Encoding.decodeWord(asciiSequence);

        if (word == null)
            word = asciiSequence.toString();

        return word;
    }

    /**
     * Encodes the UTF-8 subject text into an ASCII string using Base64 encoding.
     *
     * @return the ASCII encoded subject text.
     */
    public String decodeFromAscii() {
        return decodeFromAscii(body);
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
