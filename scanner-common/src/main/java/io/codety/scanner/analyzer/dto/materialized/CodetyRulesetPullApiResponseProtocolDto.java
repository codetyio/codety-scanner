package io.codety.scanner.analyzer.dto.materialized;

public class CodetyRulesetPullApiResponseProtocolDto {

    /*
    * Have to use array here due to obfuscation process impact the Json deserialization.
    * */
    CodetyRulesetPullApiPayloadDto[] payloads; //use array instead of List.

    CodetyConfigPullApiSettingDto setting;

    boolean success = false;
    String errorMessage;

    public CodetyRulesetPullApiResponseProtocolDto(boolean success, String errorMessage) {
        this.success = success;
        this.errorMessage = errorMessage;
    }

    public CodetyRulesetPullApiResponseProtocolDto() {
    }

    public CodetyRulesetPullApiPayloadDto[] getPayloads() {
        return payloads;
    }

    public void setPayloads(CodetyRulesetPullApiPayloadDto[] payloads) {
        this.payloads = payloads;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public CodetyConfigPullApiSettingDto getSetting() {
        return setting;
    }

    public void setSetting(CodetyConfigPullApiSettingDto setting) {
        this.setting = setting;
    }
}
