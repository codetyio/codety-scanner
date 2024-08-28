package io.codety.scanner.analyzer.cppcheck.dto;

public class CppcheckUtil {
    public static int convertCppcheckPriority(String severity) {
        int result = 3;
        if(severity == null){
            return result;
        }
        if (severity.equalsIgnoreCase("none") || severity.equalsIgnoreCase("debug")) {
            result = 1;
        } else if (severity.equalsIgnoreCase("information")) {
            result = 2;
        } else if (severity.equalsIgnoreCase("performance")) {
            result = 4;
        } else if (severity.equalsIgnoreCase("error") || severity.equalsIgnoreCase("warning")) {
            result = 5;
        }
        return result;
    }
}
