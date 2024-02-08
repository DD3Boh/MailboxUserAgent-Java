package mua;

public class ContentDispositionHeader implements Header<String> {
    private final String value;

    public ContentDispositionHeader(String value) {
        int filenameIndex = value.indexOf("filename=");
        if (filenameIndex != -1)
            this.value = value.substring(filenameIndex + 9).replace("\"", "");
        else
            this.value = null;
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
    public String toString() {
        return getType() + ": attachment; filename=\"" + getValue() + "\"";
    }
}
