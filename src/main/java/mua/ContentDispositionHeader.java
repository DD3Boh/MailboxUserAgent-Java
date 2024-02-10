package mua;

import utils.ASCIICharSequence;

/**
 * Represents a Content-Disposition header.
 * Implements the Header interface with a String value.
 */
public class ContentDispositionHeader implements Header<String> {
    /** the value of the Content-Disposition header */
    private final String value;
    /** the type of the Content-Disposition header */
    private final String dispositionType;

    /**
     * Constructs a ContentDispositionHeader object with the given value.
     * Parses the value to extract the disposition type and filename.
     * 
     * @param value the value of the Content-Disposition header
     */
    public ContentDispositionHeader(String value) {
        int typeIndex = 0;
        int filenameIndex = value.indexOf("filename=");

        if (filenameIndex != -1) {
            String type = value.substring(0, typeIndex).trim();
            this.value = value.substring(filenameIndex + 9).replace("\"", "");

            String[] parts = type.split(";");
            if (parts.length > 1) {
                this.dispositionType = parts[1].trim();
            } else {
                this.dispositionType = null;
            }
        } else {
            this.value = null;
            this.dispositionType = null;
        }
    }

    /**
     * Constructs a ContentDispositionHeader object with the given disposition type and value.
     * 
     * @param dispositionType the disposition type of the Content-Disposition header
     * @param value the value of the Content-Disposition header
     */
    public ContentDispositionHeader(String dispositionType, String value) {
        this.value = value;
        this.dispositionType = dispositionType;
    }

    /**
     * Returns the type of the Content-Disposition header.
     * 
     * @return the type of the Content-Disposition header
     */
    @Override
    public String getType() {
        return "Content-Disposition";
    }

    /**
     * Returns the value of the Content-Disposition header.
     * 
     * @return the value of the Content-Disposition header
     */
    @Override
    public String getValue() {
        return value;
    }

    /**
     * Encodes the Content-Disposition header to ASCII representation.
     * 
     * @return the ASCII representation of the Content-Disposition header
     */
    @Override
    public ASCIICharSequence encodeToASCII() {
        return ASCIICharSequence.of(getType() + ": " + dispositionType + " ; filename=\"" + value + "\"");
    }

    /**
     * Returns the string representation of the Content-Disposition header.
     * 
     * @return the string representation of the Content-Disposition header
     */
    @Override
    public String toString() {
        return getValue();
    }
}
