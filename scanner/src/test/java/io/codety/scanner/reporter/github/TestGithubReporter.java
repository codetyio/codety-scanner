package io.codety.scanner.reporter.github;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestGithubReporter {

    @Test
    void escapeMarkdown(){
        String s = ":white_circle: [generic] [require-lang]  Missing `lang` attribute in `<html>` tag";
        Assertions.assertTrue(s.contains(">"));
        s = s.replaceAll("\\<", "&lt;");
        s = s.replaceAll("\\>", "&gt;");
        Assertions.assertFalse(s.contains(">"));

    }
}
