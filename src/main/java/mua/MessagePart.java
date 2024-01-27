package mua;

import java.util.List;

public class MessagePart {
    private List<Header> header;

    /**
     * Constructs a MessagePart object with the specified header.
     *
     * @param header the header of the message part
     */
    public MessagePart(Header header) {
        this.header = header;
    }

    /**
     * Returns the header of the message part.
     *
     * @return the header
     */
    public Header getHeader() {
        return header;
    }
}
