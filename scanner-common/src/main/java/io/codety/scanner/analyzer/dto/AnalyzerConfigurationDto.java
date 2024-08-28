package io.codety.scanner.analyzer.dto;

import io.codety.scanner.analyzer.dto.materialized.CodetyConfigPullApiSettingDto;

import java.util.*;

/*
The CodetyPullAPI result will be converted to this class for limiting the scope of the CodetyPullAPi, and do not spread the usage of external DTO .
* */
public class AnalyzerConfigurationDto {
    List<AnalyzerConfigurationDetailDto> configurationDetailDtoList = new ArrayList<>();

    private transient CodetyConfigPullApiSettingDto settingDto;

    public List<AnalyzerConfigurationDetailDto> getConfigurationDetailDtoList() {
        return configurationDetailDtoList;
    }

    public void setConfigurationDetailDtoList(List<AnalyzerConfigurationDetailDto> configurationDetailDtoList) {
        this.configurationDetailDtoList = configurationDetailDtoList;
    }

    public CodetyConfigPullApiSettingDto getSettingDto() {
        return settingDto;
    }

    public void setSettingDto(CodetyConfigPullApiSettingDto settingDto) {
        this.settingDto = settingDto;
    }
}
