package io.codety.scanner.analyzer.codety;

import io.codety.scanner.analyzer.dto.CodetyMatchingRule;
import io.codety.scanner.analyzer.dto.CodetyMatchingRuleRegistration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.regex.Pattern;

@SpringBootTest
public class CodetyDefaultFactoryAnalyzerTest {

    @Test
    void testPocRules(){
        List<CodetyMatchingRule> list = CodetyMatchingRuleRegistration.DEFAULT_REGEX_RULES;
        for(CodetyMatchingRule codetyMatchingRule : list){
            System.out.println(codetyMatchingRule.getRuleId() + "\t\t" + codetyMatchingRule.getRegex());

            Pattern pattern = Pattern.compile(codetyMatchingRule.getRegex());
            boolean b = pattern.matcher("oiwejf ighp_111111111111111111111111111111111111").find();


        }
    }



}
