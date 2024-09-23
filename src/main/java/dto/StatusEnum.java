package dto;

public enum StatusEnum {
    AVAILABLE ("available"),
    PENDING("pending"),
    SOLD("sold");

    private final String textValue;

    StatusEnum(String textValue) {
        this.textValue = textValue;
    }

    public String getTextValue() {
        return textValue;
    }
}
