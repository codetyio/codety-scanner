package io.codety.scanner.analyzer.dto.unmaterialized;

public class CodetyUnmaterializedCodeAnalyzerSetting {
    CodetyUnmaterializedPluginGroup[] pluginGroups;
    //String global setting;


    public CodetyUnmaterializedPluginGroup[] getPluginGroups() {
        return pluginGroups;
    }

    public void setPluginGroups(CodetyUnmaterializedPluginGroup[] pluginGroups) {
        this.pluginGroups = pluginGroups;
    }
}
