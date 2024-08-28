//package io.codety.analyzer.integration.pmd;
//
//import io.codety.job.analyzer.dto.CodeAnalysisResultDto;
//import io.codety.job.analyzer.dto.CodeAnalyzerType;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Path;
//
//@Service
//public class JavaPmdDuplicateCodeAnalyzer {
//
//    @Value("${codety.analyzer.java.pmd.path}")
//    String pmdPath;
//    public CodeAnalysisResultDto analyzeCode(String sourcePath, String rulesetPath) {
//        CodeAnalysisResultDto codeAnalysisResultDto = new CodeAnalysisResultDto(CodeAnalyzerType.Java_Source);
//        StringBuilder command = new StringBuilder("pmd check ")
//                .append(" --dir ").append(sourcePath)
//                .append(" -f ").append("csv")
//                .append(" --rulesets ").append(rulesetPath)
//        ;
//
//        Path path = Path.of(pmdPath);
//
//        try {
//            Process exec = Runtime.getRuntime().exec(path + File.separator + command.toString());
//            String result = new String(exec.getInputStream().readAllBytes());
//            exec.waitFor();
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//
//        return codeAnalysisResultDto;
//    }
//}
