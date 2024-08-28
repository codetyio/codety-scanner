package io.codety.scanner.analyzer.pmd;

import io.codety.scanner.util.AnalyzerMatchingRule;
import io.codety.scanner.util.AnalyzerMatchingRuleRegistry;
import io.codety.scanner.util.CodetyConsoleLogger;
import io.codety.scanner.util.CodetyConstant;
import io.codety.scanner.analyzer.pmd.dto.CodeAnalyzerSettingGroupDto;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;

@Component
public class SourceCodeDirectoryLayoutAnalyzer {

    /*
    * maven and gradle: (https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html):
    *
    *   src/main/java,
    *   src/test/java, src/it,
    * */


    public CodeAnalyzerSettingGroupDto analyzeSourceDirectory(String path) {
        CodeAnalyzerSettingGroupDto codeAnalyzerSettingGroupDto = new CodeAnalyzerSettingGroupDto();

        File gitCodeBasePath = Path.of(path).toFile();

        if(!gitCodeBasePath.exists() || !gitCodeBasePath.isDirectory()){
            CodetyConsoleLogger.info(CodetyConstant.INFO_CANNOT_LOCATE_GIT_PATH + gitCodeBasePath);
            return codeAnalyzerSettingGroupDto;
        }
        CodetyConsoleLogger.debug("Workdir: " + gitCodeBasePath + " \t, contains items: " + gitCodeBasePath.listFiles().length);
//        for(File sub : gitCodeBasePath.listFiles()){
//            CodetyConsoleLogger.debug(sub.getName());
//        }
        int maxPathDepth = 6;

        for(AnalyzerMatchingRule rule : AnalyzerMatchingRuleRegistry.getRuleList()) {
            detectSourcePath(rule, gitCodeBasePath, maxPathDepth, codeAnalyzerSettingGroupDto);
        }

        CodetyConsoleLogger.debug("Setting group: " + codeAnalyzerSettingGroupDto);

        return codeAnalyzerSettingGroupDto;
    }

    private void detectSourcePath(AnalyzerMatchingRule rule, File rootPath, int maxPathDepth, CodeAnalyzerSettingGroupDto codeAnalyzerSettingGroupDto) {
        if(!rootPath.exists() || !rootPath.isDirectory() || maxPathDepth < 0){
            return;
        }

        for(File child : rootPath.listFiles()){
            if(!child.isDirectory()){
                continue;
            }

            if(child.getPath().contains(rule.getPath())){
                codeAnalyzerSettingGroupDto.append(rule.getCodeAnalyzerType(), child.getAbsolutePath());
                break;
            }

            detectSourcePath(rule, child, maxPathDepth-1, codeAnalyzerSettingGroupDto);
        }

    }


}
