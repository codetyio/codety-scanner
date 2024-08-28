package io.codety.scanner.service;

import io.codety.scanner.analyzer.pmd.SourceCodeDirectoryLayoutAnalyzer;
import io.codety.scanner.analyzer.pmd.dto.CodeAnalyzerSettingGroupDto;
import io.codety.scanner.analyzer.pmd.dto.JavaPmdCodeAnalysisSettingDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles(value = "dev")
public class SourceCodeDirectoryLayoutAnalyzerTest {

    @Autowired
    SourceCodeDirectoryLayoutAnalyzer sourceCodeDirectoryLayoutAnalyzer;

    @Test
    public void analyzeSourceDirectoryLayout(){

        String path = Paths.get("../","code-issue-examples", "java-pmd").toFile().getAbsoluteFile().getPath();
        CodeAnalyzerSettingGroupDto codeAnalyzerSettingGroupDto = sourceCodeDirectoryLayoutAnalyzer.analyzeSourceDirectory(path);
        List<JavaPmdCodeAnalysisSettingDto> codeAnalysisSettingDtos = codeAnalyzerSettingGroupDto.getCodeAnalysisSettingDtos();
        assertTrue(codeAnalysisSettingDtos.size() > 1);

        //src/test/java, module_b/src/test/java, module_a/src/test/java
        List<String> javaSourcePathList = codeAnalysisSettingDtos.get(0).getSourceCodePathList();
        assertEquals(javaSourcePathList.size(), 4);
        assertTrue(javaSourcePathList.get(0).contains("src/main/java"));


        List<String> javaTestPathList = codeAnalysisSettingDtos.get(1).getSourceCodePathList();
        assertEquals(javaTestPathList.size(), 4);
        assertTrue(javaTestPathList.get(0).contains("src/test/java"));
    }

}
