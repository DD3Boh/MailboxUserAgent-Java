package mua;

import utils.ASCIICharSequence;

public class ContentDispositionHeader implements Header<String> {
    private final String value;
    private final String dispositionType;

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

    public ContentDispositionHeader(String dispositionType, String value) {
        this.value = value;
        this.dispositionType = dispositionType;
    }

    @Override
    public String getType() {
        return "Content-Disposition";
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public ASCIICharSequence encodeToASCII() {
        return ASCIICharSequence.of(getType() + ": " + dispositionType + " ; filename=\"" + value + "\"");
    }

    @Override
    public String toString() {
        return getValue();
    }
}
