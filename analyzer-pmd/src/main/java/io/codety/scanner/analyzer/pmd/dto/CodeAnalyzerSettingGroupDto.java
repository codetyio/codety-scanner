package io.codety.scanner.analyzer.pmd.dto;

import io.codety.scanner.util.CodeSourceDirectoryType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodeAnalyzerSettingGroupDto {

    private Map<CodeSourceDirectoryType, JavaPmdCodeAnalysisSettingDto> analyzerTypeListMap = new HashMap();

    List<JavaPmdCodeAnalysisSettingDto> codeAnalysisSettingDtos = new ArrayList<>();

    public void append(CodeSourceDirectoryType codeSourceDirectoryType, String path){
        if(analyzerTypeListMap.containsKey(codeSourceDirectoryType)){
            JavaPmdCodeAnalysisSettingDto javaPmdCodeAnalysisSettingDto = analyzerTypeListMap.get(codeSourceDirectoryType);
            javaPmdCodeAnalysisSettingDto.getSourceCodePathList().add(path);
        }else{
            JavaPmdCodeAnalysisSettingDto codeAnalysisSettingDto = new JavaPmdCodeAnalysisSettingDto(codeSourceDirectoryType);
            codeAnalysisSettingDto.getSourceCodePathList().add(path);
            analyzerTypeListMap.put(codeSourceDirectoryType, codeAnalysisSettingDto);
            codeAnalysisSettingDtos.add(codeAnalysisSettingDto);
        }

    }

    public List<JavaPmdCodeAnalysisSettingDto> getCodeAnalysisSettingDtos() {
        return codeAnalysisSettingDtos;
    }

    @Override
    public String toString() {
        String tmp = "";
        if(codeAnalysisSettingDtos!=null){
            for(JavaPmdCodeAnalysisSettingDto dto : codeAnalysisSettingDtos){
                tmp+= (" " + dto + ", ");
            }
        }
        return tmp;
    }
}
