package io.codety.scanner.analyzer.dto.unmaterialized;

public class CodetyUnmaterializedRuleSetting {
    String key;
    String value;
    String valueDataType; //int, str,

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValueDataType() {
        return valueDataType;
    }

    public void setValueDataType(String valueDataType) {
        this.valueDataType = valueDataType;
    }
}
