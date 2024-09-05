package io.codety.common.dto;

import java.util.HashMap;
import java.util.Map;

public enum CodeAnalyzerType {
    codety(1)
    , pmd(2)
    , cppcheck(3)
    , eslint(4)
    , pylint(5)
    , trivy(6)
    , checkov(10)
    , golangcilint(20)
    , scalastyle(30)
    , rubocop(40)
    ;
    public final int codeAnalyzerType;

    private static final Map<Integer, CodeAnalyzerType> byCode = new HashMap<>();

    static {
        for (CodeAnalyzerType e : values()) {
            byCode.put(e.codeAnalyzerType, e);
        }
    }

    private CodeAnalyzerType(int n) {
        this.codeAnalyzerType = n;
    }

    public static CodeAnalyzerType valueOfCode(int codeAnalyzerType) {
        return byCode.get(codeAnalyzerType);
    }

//    public static final int pmd = 2;
//    public static final int cppcheck = 3;
}
