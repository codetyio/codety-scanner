package io.codety.scanner.util;

import java.util.ArrayList;
import java.util.List;

/*
* Contains a list of rules which can be used for matching the folders.
* */
public class AnalyzerMatchingRuleRegistry {
    private static final String javaSourceStructure = "src/main/java";
    private static final String javaTestStructure = "src/test/java";

    public static List<AnalyzerMatchingRule> getRuleList() {
        ArrayList<AnalyzerMatchingRule> arrayList = new ArrayList();

        arrayList.add(new AnalyzerMatchingRule(CodeSourceDirectoryType.source_code, javaSourceStructure));

        arrayList.add(new AnalyzerMatchingRule(CodeSourceDirectoryType.test_case_code, javaTestStructure));
        return arrayList;
    }
}
