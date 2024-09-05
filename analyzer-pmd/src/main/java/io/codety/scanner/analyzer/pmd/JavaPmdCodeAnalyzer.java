package io.codety.scanner.analyzer.pmd;

import io.codety.common.dto.LanguageType;
import io.codety.scanner.analyzer.CodeAnalyzerInterface;
import io.codety.scanner.analyzer.pmd.dto.CodeAnalyzerSettingGroupDto;
import io.codety.scanner.analyzer.pmd.dto.JavaPmdCodeAnalysisSettingDto;
import io.codety.scanner.analyzer.dto.AnalyzerConfigurationDetailDto;
import io.codety.scanner.reporter.dto.CodeAnalysisIssueDto;
import io.codety.scanner.reporter.dto.CodeAnalysisResultDto;
import io.codety.common.dto.CodeAnalyzerType;
import io.codety.scanner.service.dto.AnalyzerRequest;
import io.codety.scanner.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JavaPmdCodeAnalyzer implements CodeAnalyzerInterface {

    @Autowired
    SourceCodeDirectoryLayoutAnalyzer sourceCodeDirectoryLayoutAnalyzer;

    private static Logger logger = LoggerFactory.getLogger(JavaPmdCodeAnalyzer.class);

    @Value(CodetyConstant.PMD_BIN_PATH)
    String pmdPath;

    @Value("${codety.analyzer.java.ruleset.path}")
    String pmdRulesetPath;

    private static final String bash = "bash";
    private static final String pmd = "pmd";
    private static final String check = "check";
    private static final String dir = "--dir";
    private static final String rulesetsParam = "--rulesets";
    private static final String nocacheParam = "-no-cache";
    public CodeAnalysisResultDto analyzeCode(JavaPmdCodeAnalysisSettingDto settingDto, AnalyzerConfigurationDetailDto analyzerConfigurationDetailDto, String baseSourcePath) {
        String sourcePath = settingDto.getSourceCodePathList().stream().collect(Collectors.joining(","));
        String rulesetPath = settingDto.getRulesetPathList().stream().collect(Collectors.joining(","));
        CodeAnalysisResultDto codeAnalysisResultDto = new CodeAnalysisResultDto(analyzerConfigurationDetailDto.getLanguage(), analyzerConfigurationDetailDto.getCodeAnalyzerType());
        codeAnalysisResultDto.setDisplayTitle(StringUtil.toCamelCaseWord(analyzerConfigurationDetailDto.getLanguage()) + " " + settingDto.getCodeAnalyzerType().label);
        String[] command = {bash, pmd, check, dir, sourcePath, "-f", "csv", rulesetsParam, rulesetPath, nocacheParam};

        try {
            RuntimeExecUtil.RuntimeExecResult runtimeExecResult = RuntimeExecUtil.exec(command, pmdPath, 60, false, null);
            parsePmdResult(runtimeExecResult.getSuccessOutput(), baseSourcePath, codeAnalysisResultDto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return codeAnalysisResultDto;
    }

    private List<CodeAnalysisIssueDto> parsePmdResult(String pmdCsvResult, String baseSourcePath, CodeAnalysisResultDto codeAnalysisResultDto) {
        List<CodeAnalysisIssueDto> result = new ArrayList<>();
        pmdCsvResult = pmdCsvResult.replaceAll("\\\"", "\"");
        pmdCsvResult = pmdCsvResult.replaceAll("\"", "");
        String[] lines = pmdCsvResult.split("\n");
        //           0         1          2     3         4        5           6           7
        //columns: "Problem","Package","File","Priority","Line","Description","Rule set","Rule"
        for(int i=1; i<lines.length; i++){
            String[] ary = lines[i].split(",");
            CodeAnalysisIssueDto issueDto = new CodeAnalysisIssueDto();
            issueDto.setPackagePath(ary[1]);
            Integer startLineNumber = Integer.valueOf(ary[4]);
            issueDto.setStartLineNumber(startLineNumber);
            issueDto.setDescription(ary[5]);
            String fileUri = ary[2].substring(baseSourcePath.length());
            fileUri = fileUri.charAt(0) == '/' ? fileUri.substring(1) : fileUri;
            issueDto.setFilePath(fileUri);
            issueDto.setIssueCategory(ary[6]);
            issueDto.setIssueCode(ary[7]);
            issueDto.setPriority(Integer.valueOf(ary[3]));
            result.add(issueDto);
            codeAnalysisResultDto.addIssue(issueDto);

        }

        return result;
    }
    String useCustomRuleset = "Using custom ruleset. ";
    String useDefaultRuleset = "Using default ruleset.";

    @Override
    public List<CodeAnalysisResultDto> analyzeCode(AnalyzerConfigurationDetailDto runnerConfiguration, AnalyzerRequest request) {
        CodetyConsoleLogger.info("Scanning "+runnerConfiguration.getLanguage()+" code via "+runnerConfiguration.getCodeAnalyzerType().name()+"...");
        String baseSourcePath = request.getLocalGitRepoPath();

        List<CodeAnalysisResultDto> resultSetDto = new ArrayList<>();
        if(runnerConfiguration.getLanguage().equalsIgnoreCase("java")) {
            CodeAnalyzerSettingGroupDto codeAnalyzerSettingGroupDto = sourceCodeDirectoryLayoutAnalyzer.analyzeSourceDirectory(baseSourcePath);
            CodetyConsoleLogger.debug("Found number of source path: " + codeAnalyzerSettingGroupDto.getCodeAnalysisSettingDtos().size());
            for (JavaPmdCodeAnalysisSettingDto javaPmdCodeAnalysisSettingDto : codeAnalyzerSettingGroupDto.getCodeAnalysisSettingDtos()) {
                if (runnerConfiguration != null && runnerConfiguration.getFile() != null) {
                    CodetyConsoleLogger.debug(useCustomRuleset);
                    javaPmdCodeAnalysisSettingDto.setRulesetPathList(List.of(runnerConfiguration.getFile().getAbsolutePath()));
                } else {

                    CodetyConsoleLogger.debug(useDefaultRuleset);
                    javaPmdCodeAnalysisSettingDto.setRulesetPathList(List.of(pmdRulesetPath));
                }

                CodeAnalysisResultDto codeAnalysisResultDto = this.analyzeCode(javaPmdCodeAnalysisSettingDto, runnerConfiguration, baseSourcePath);
                resultSetDto.add(codeAnalysisResultDto);
            }
        }else{
            JavaPmdCodeAnalysisSettingDto javaPmdCodeAnalysisSettingDto = new JavaPmdCodeAnalysisSettingDto(CodeSourceDirectoryType.source_code);
            javaPmdCodeAnalysisSettingDto.setRulesetPathList(List.of(runnerConfiguration.getFile().getAbsolutePath()));
            javaPmdCodeAnalysisSettingDto.setSourceCodePathList(List.of(request.getLocalGitRepoPath()));
            CodeAnalysisResultDto codeAnalysisResultDto = this.analyzeCode(javaPmdCodeAnalysisSettingDto, runnerConfiguration, baseSourcePath);
            resultSetDto.add(codeAnalysisResultDto);
        }

        // pmd analyzer end =========================================
        return resultSetDto;
    }

    @Override
    public List<CodeAnalysisResultDto> analyzeCode(AnalyzerRequest request) {
        return analyzeCode(new AnalyzerConfigurationDetailDto(LanguageType.java, CodeAnalyzerType.pmd), request);
    }
}
