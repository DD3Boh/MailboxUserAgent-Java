package mua;

import java.util.List;
import java.util.ArrayList;

import utils.ASCIICharSequence;
import utils.Fragment;

public class MessagePart {
    private List<Header<?>> headers;
    private ASCIICharSequence body;
    private Fragment fragment;

    /**
     * Construct a MessagePart object with the specified Fragment.
     *
     * @param fragment the Fragment
     */
    public MessagePart(Fragment fragment) {
        this.headers = new ArrayList<>();
        this.headers.add(parseHeader(fragment.rawHeaders().get(0)));
        this.body = fragment.rawBody();
        this.fragment = fragment;
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
        return headers;
    }

    /**
     * Returns the body of the message part.
     *
     * @return the body
     */
    public ASCIICharSequence getBody() {
        return body;
    }

    /**
     * Returns the Fragment of the message part.
     *
     * @return the Fragment
     */
    public Fragment getFragment() {
        return fragment;
    }
}
