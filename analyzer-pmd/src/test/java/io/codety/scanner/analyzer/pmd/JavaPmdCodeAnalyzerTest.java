package io.codety.scanner.analyzer.pmd;

import io.codety.scanner.analyzer.dto.AnalyzerConfigurationDetailDto;
import io.codety.scanner.analyzer.pmd.JavaPmdCodeAnalyzer;
import io.codety.scanner.reporter.dto.CodeAnalysisResultDto;
import io.codety.scanner.util.CodeSourceDirectoryType;
import io.codety.scanner.analyzer.pmd.dto.JavaPmdCodeAnalysisSettingDto;
import io.codety.common.dto.CodeAnalyzerType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.nio.file.Paths;

@SpringBootTest
@ActiveProfiles(value = "dev")
public class JavaPmdCodeAnalyzerTest {

    @Autowired
    JavaPmdCodeAnalyzer javaPmdCodeAnalyzer;

    @Test
    public void testAnalyze() {
        String commitSha = "blablablabla";
        String path1 = Paths.get("../", "code-issue-examples", "java-pmd", "module_api").toFile().getAbsoluteFile().getPath();
        String path2 = Paths.get("../", "code-issue-examples", "java-pmd", "module_scheduler").toFile().getAbsoluteFile().getPath();
        JavaPmdCodeAnalysisSettingDto settingDto = new JavaPmdCodeAnalysisSettingDto(CodeSourceDirectoryType.source_code);
        settingDto.getSourceCodePathList().add(path1);
        settingDto.getSourceCodePathList().add(path2);
        String rulePath = Paths.get("../", "code-issue-examples", "java-pmd", "pmd_quickstart.xml").toFile().getAbsoluteFile().getPath();
        settingDto.getRulesetPathList().add(rulePath);

        AnalyzerConfigurationDetailDto analyzerConfigurationDetailDto = new AnalyzerConfigurationDetailDto();
        analyzerConfigurationDetailDto.setLanguage("java");
        analyzerConfigurationDetailDto.setCodeAnalyzerType(CodeAnalyzerType.pmd);

        CodeAnalysisResultDto result = javaPmdCodeAnalyzer.analyzeCode(settingDto, analyzerConfigurationDetailDto, "/base-path/");
        Assertions.assertTrue(result.getIssuesByFile().size() >= 3);
    }


    @Test
    public void testTestCaseTooLong(){
        String commitSha = "blablablabla";
        String path1 = Paths.get("../", "code-issue-examples", "java-customized-rules", "testCaseTooLong").toFile().getAbsoluteFile().getPath();
        JavaPmdCodeAnalysisSettingDto settingDto = new JavaPmdCodeAnalysisSettingDto(CodeSourceDirectoryType.source_code);
        settingDto.getSourceCodePathList().add(path1);
        String rulePath = Paths.get("../", "code-issue-examples", "java-customized-rules", "testCaseTooLong", "testCaseTooLong.xml").toFile().getAbsoluteFile().getPath();
        settingDto.getRulesetPathList().add(rulePath);

        AnalyzerConfigurationDetailDto analyzerConfigurationDetailDto = new AnalyzerConfigurationDetailDto();
        analyzerConfigurationDetailDto.setLanguage("java");
        analyzerConfigurationDetailDto.setCodeAnalyzerType(CodeAnalyzerType.pmd);

        CodeAnalysisResultDto result = javaPmdCodeAnalyzer.analyzeCode(settingDto, analyzerConfigurationDetailDto, "/base-path/");
        Assertions.assertTrue(result.getIssuesByFile().size() > 0);

    }

}
