package io.codety.scanner.analyzer.cppcheck;

import io.codety.scanner.analyzer.cppcheck.dto.*;
import io.codety.scanner.reporter.dto.CodeAnalysisIssueDto;

import java.util.ArrayList;
import java.util.List;

public class CppcheckResultConverter {

    public static List<CodeAnalysisIssueDto> convertResult(String errorOutput) throws Exception {

        if (errorOutput == null || errorOutput.isEmpty()) {
            return null;
        }

        List<CodeAnalysisIssueDto> result = new ArrayList<>();
        String[] lines = errorOutput.split("\n");
/*
example:
##@@_@@##arrayIndexOutOfBounds@@@@@@788@@@@@@analyzer/src/test/resources/cppcheck/cpp-code-with-issues/deleting.cpp@@@@@@25@@@@@@error@@@@@@Array 'a[10]' accessed at index 10, which is out of bounds.
|--@@analyzer/src/test/resources/cppcheck/cpp-code-with-issues/deleting.cpp@@@@@@7@@@@@@10@@@@@@Assignment 'n=10', assigned value is 10
|--@@analyzer/src/test/resources/cppcheck/cpp-code-with-issues/deleting.cpp@@@@@@23@@@@@@27@@@@@@Assuming that condition 'i<n' is not redundant
|--@@analyzer/src/test/resources/cppcheck/cpp-code-with-issues/deleting.cpp@@@@@@25@@@@@@11@@@@@@Array index out of bounds
##@@_@@##uninitvar@@@@@@457@@@@@@analyzer/src/test/resources/cppcheck/cpp-code-with-issues/deleting.cpp@@@@@@23@@@@@@warning@@@@@@Uninitialized variable: position
          0              1                  2                                                                3         4              5
|--@@analyzer/src/test/resources/cppcheck/cpp-code-with-issues/deleting.cpp@@@@@@20@@@@@@13@@@@@@Assuming condition is false
|--@@analyzer/src/test/resources/cppcheck/cpp-code-with-issues/deleting.cpp@@@@@@23@@@@@@15@@@@@@Uninitialized variable: position
      0                                                                           1       2              3
* */

        String[] issueHeader = null;
        boolean issueContainsLocationListInformation = false; //default has to be false.
        for(int i=0; i<lines.length; i++){
            String line = lines[i];

            if(line.startsWith(CppcheckCodeAnalyzer.cppcheckIssueResultPrefix)){
                if(issueContainsLocationListInformation){
                    //there's no location for this record, should be an exception.
                    throw new RuntimeException("Unexpected case where a cppcheck issue result does not contain any issueLocation info.");
                }
                issueHeader = line.substring(CppcheckCodeAnalyzer.cppcheckIssueResultPrefix.length()).split(CppcheckCodeAnalyzer.cppcheckResultLocationSeparator);
                issueContainsLocationListInformation = true;
            }else if(line.startsWith(CppcheckCodeAnalyzer.cppcheckResultLocationPrefix)){
                issueContainsLocationListInformation = false;
                String[] errorLocationAry = line.substring(CppcheckCodeAnalyzer.cppcheckResultLocationPrefix.length()).split(CppcheckCodeAnalyzer.cppcheckResultLocationSeparator);
                CodeAnalysisIssueDto resultRecordDto = new CodeAnalysisIssueDto();
                resultRecordDto.setIssueCode(issueHeader[0]);
                resultRecordDto.setCweId(convertStrToInt(issueHeader[1]));
                resultRecordDto.setPriority(CppcheckUtil.convertCppcheckPriority(issueHeader[4]));
                resultRecordDto.setIssueCategory(issueHeader[4]); //lineNumber
                String filePath = errorLocationAry[0];
                if(filePath!=null && filePath.startsWith("/")){
                    filePath = filePath.substring(1);
                }
                resultRecordDto.setFilePath(filePath);
                resultRecordDto.setStartLineNumber(convertStrToInt(errorLocationAry[1]));
                String info = errorLocationAry[3];
                if(info != null){
                    resultRecordDto.setDescription(info);
                }else{
                    resultRecordDto.setDescription(issueHeader[5]);
                }
                result.add(resultRecordDto);
            }

        }

        return result;
    }

    private static Integer convertStrToInt(String s) {
        if(s == null || s.isEmpty()){
            return null;
        }
        return Integer.valueOf(s);
    }
}
