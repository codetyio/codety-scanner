package io.codety.scanner.analyzer.dto.materialized;

public class CodetyConfigPullApiSettingDto {
    int accountType;
    boolean offlineMode;

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public boolean isOfflineMode() {
        return offlineMode;
    }

    public void setOfflineMode(boolean offlineMode) {
        this.offlineMode = offlineMode;
    }
}
