package io.codety.scanner.analyzer.codety;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.codety.common.dto.LanguageType;
import io.codety.scanner.analyzer.CodeAnalyzerInterface;
import io.codety.scanner.analyzer.dto.AnalyzerConfigurationDetailDto;
import io.codety.scanner.analyzer.codety.dto.CodetyRegexAnalyzerRule;
import io.codety.scanner.analyzer.codety.dto.CodetyRegexAnalyzerRuleList;
import io.codety.scanner.analyzer.dto.CodetyMatchingRule;
import io.codety.scanner.analyzer.dto.CodetyMatchingRuleRegistration;
import io.codety.scanner.reporter.dto.CodeAnalysisIssueDto;
import io.codety.scanner.reporter.dto.CodeAnalysisResultDto;
import io.codety.common.dto.CodeAnalyzerType;
import io.codety.scanner.service.dto.AnalyzerRequest;
import io.codety.scanner.util.CodetyConsoleLogger;
import io.codety.scanner.util.JsonFactoryUtil;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CodetyRegexCodeAnalyzer implements CodeAnalyzerInterface {
    private static final Set<String> IGNORE_FILE_TYPES = Set.of(
            ".jar", ".zip", ".iso", ".rpm", ".exe"
    );
    private static final int SKIP_BIG_FILE_LENGTH_UPPER_LIMIT = 10*1000*1000; // the file above this size will be skipped.
    private static final int FILE_READ_BUFFER = 1024000;
    @Override
    public List<CodeAnalysisResultDto> analyzeCode(AnalyzerConfigurationDetailDto runnerConfiguration, AnalyzerRequest request) {
        CodetyConsoleLogger.debug("Scanning code via Codety Secretx...");
        String absoluteBasePath = request.getLocalGitRepoPath();

        List<CodeAnalysisResultDto> list = new ArrayList<>();
        List<CodetyRegexAnalyzerRule> inputRuleList = null;

        try {
            if(request.getCodetyAccountType() == -1){
                inputRuleList = CodetSecretxRuleFactory.getDefaultCodetyRules();
            }else {
                String payload = runnerConfiguration.getPayload();
                CodetyRegexAnalyzerRuleList codetyRegexAnalyzerRuleList = JsonFactoryUtil.objectMapper.readValue(payload, CodetyRegexAnalyzerRuleList.class);
                inputRuleList = codetyRegexAnalyzerRuleList.getRules();
            }
        } catch (JsonProcessingException e) {
            CodetyConsoleLogger.debug("Failed to deserialize codety analyzer from payload", e);
            CodetyConsoleLogger.info("Skipping codety due to the deserialization warning");
            return list;
        }

        CodeAnalysisResultDto resultDto = new CodeAnalysisResultDto("Secrets detection", CodeAnalyzerType.codety);
        list.add(resultDto);
        if(inputRuleList == null || inputRuleList.isEmpty()){
            return list;
        }
        CodetyConsoleLogger.debug("Parsing payload completed, ruleListSize:" + inputRuleList.size());

        Map<String, Pattern> regexCacheMap = createRegexPatternMap(inputRuleList);

        String localGitRepoPath = request.getLocalGitRepoPath();
        File file = new File(localGitRepoPath);
        Queue<File> q = new LinkedList<>();
        q.add(file);
        CodetyConsoleLogger.debug("Start scanning files via Codety analyzer ");
        while(q.size() > 0){
            int queueSize = q.size();
            for(int i=0; i<queueSize; i++){
                File pollFile = q.poll();
                if(pollFile.isDirectory()){
                    for(File child : pollFile.listFiles()){
                        String lowerCase = child.getName().toLowerCase();
                        boolean skip = false;
                        for(String suffix : IGNORE_FILE_TYPES){
                            if(lowerCase.endsWith(suffix)){
                                skip = true;
                                break;
                            }
                        }
                        if(skip){
                            continue;
                        }

                        q.add(child);
                    }
                }else{
                    scanFile(pollFile, resultDto, inputRuleList, regexCacheMap, absoluteBasePath);
                }

            }

        }

//        for(CodeAnalysisIssueDto tmpIssue: resultDto.getIssues()){
//            CodetyConsoleLogger.debug("detected issue from Codety analyzer:  " + tmpIssue.getFilePath() + " " + tmpIssue.getStartLineNumber());
//        }
//        if(resultDto.getIssuesByFile().size() > 0){

//        }
        return list;
    }

    @Override
    public List<CodeAnalysisResultDto> analyzeCode(AnalyzerRequest request) {
        return analyzeCode(new AnalyzerConfigurationDetailDto(LanguageType.all, CodeAnalyzerType.codety ), request);
    }

    private static Map<String, Pattern> createRegexPatternMap(List<CodetyRegexAnalyzerRule> inputRuleList) {
        Map<String, Pattern> regexCacheMap = new HashMap<>();
        for(CodetyRegexAnalyzerRule rule : inputRuleList){
            try {
                Pattern pattern = Pattern.compile(rule.getRegex());
                pattern.matcher("").find();
                regexCacheMap.putIfAbsent(rule.getRegex(), pattern);
            }catch (Exception e){
                CodetyConsoleLogger.debug("Failed to validate regex for " + rule.getIssueCode() + " " + rule.getRegex(), e);
            }
        }
        return regexCacheMap;
    }

    private static final int exceptionLoggingLimitWithinCurrentScan = 5;
    private int errorCount = 0;//count the number of exceptions.
    private void scanFile(File file, CodeAnalysisResultDto analysisResultDto, List<CodetyRegexAnalyzerRule> rules, Map<String, Pattern> regexMap, String absoluteBasePath) {
        int line = 0;
        try {
            if(file == null || !file.canRead() || file.length() > SKIP_BIG_FILE_LENGTH_UPPER_LIMIT){
                return;
            }
            FileReader in = new FileReader(file);

            BufferedReader bufferedReader = new BufferedReader(in, FILE_READ_BUFFER);

            while(true){
                line++;
                String s = bufferedReader.readLine();
                if(s == null){
                    break;
                }
                if(s.isEmpty()){
                    continue;
                }

                if(rules == null || rules.isEmpty()) {
                    for (CodetyMatchingRule codetyMatchingRule : CodetyMatchingRuleRegistration.DEFAULT_REGEX_RULES) {
                        Matcher matcher = codetyMatchingRule.getPattern().matcher(s);
                        if (matcher.find()) {
                            CodeAnalysisIssueDto issueDto = new CodeAnalysisIssueDto();
                            issueDto.setIssueCode(codetyMatchingRule.getRuleId());
                            issueDto.setIssueCategory("security");
                            issueDto.setDescription("issue detected " + codetyMatchingRule.getRuleId());
                            String path = file.getAbsolutePath();
                            if(path.startsWith(absoluteBasePath)){
                                path = path.substring(absoluteBasePath.length()+1);
                            }
                            issueDto.setFilePath(path);
                            issueDto.setStartLineNumber(line);
                            issueDto.setEndLineNumber(line);
                            issueDto.setPriority(codetyMatchingRule.getPriority());
                            analysisResultDto.addIssue(issueDto);
                        }
                    }
                } else {
                    for (CodetyRegexAnalyzerRule codetyMatchingRule : rules) {
                        String regex = codetyMatchingRule.getRegex();
                        if (regex == null) {
                            continue;
                        }
                        Pattern pattern = regexMap.get(regex);
                        if (pattern == null) {
                            continue;
                        }

                        Matcher matcher = pattern.matcher(s);

                        if (matcher.find()) {
                            CodeAnalysisIssueDto issueDto = new CodeAnalysisIssueDto();
                            issueDto.setIssueCode(codetyMatchingRule.getIssueCode());
                            issueDto.setIssueCategory(codetyMatchingRule.getIssueCategory());
                            issueDto.setDescription(codetyMatchingRule.getDescription());
                            String path = file.getAbsolutePath();
                            if (path.startsWith(absoluteBasePath)) {
                                //get relative path
                                path = path.substring(absoluteBasePath.length() + 1);
                            }
                            issueDto.setFilePath(path);
                            issueDto.setStartLineNumber(line);
                            issueDto.setEndLineNumber(line);
                            issueDto.setPriority(codetyMatchingRule.getPriority());
                            analysisResultDto.addIssue(issueDto);
                        }
                    }
                }

            }
            bufferedReader.close();
            in.close();

        } catch (Exception e) {
            if(++errorCount < exceptionLoggingLimitWithinCurrentScan) {
                CodetyConsoleLogger.debug("Failed to run Codety analyzer for file" + file.getAbsoluteFile() + ":" + line, e);
                CodetyConsoleLogger.info("Skipping Codety regex analyzer due to issues");
            }
        }
    }

}
