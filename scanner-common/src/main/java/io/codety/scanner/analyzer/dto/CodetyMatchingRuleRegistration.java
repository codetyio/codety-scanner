package io.codety.scanner.analyzer.dto;

import java.util.List;

public class CodetyMatchingRuleRegistration {

    public static final List<CodetyMatchingRule> DEFAULT_REGEX_RULES = List.of(
             new CodetyMatchingRule(5,"aws-access-key", "(A3T[0-9A-Z]|AKIA|AGPA|AIDA|AROA|AIPA|ANPA|ANVA|ASIA)[0-9A-Z]{16}")
            ,new CodetyMatchingRule(5,"github-access-token", "(ghu|ghs|ghr|gho|ghp)_[0-9a-zA-Z]{36}")
            ,new CodetyMatchingRule(5,"github-fine-grained-token", "github_pat_[a-z0-9A-Z]{22}_[a-z0-9A-Z]{59}")
            ,new CodetyMatchingRule(5,"gitlab-personal-access-token", "glpat-[0-9a-zA-Z\\-\\_]{20}")
            ,new CodetyMatchingRule(4,"twitter-token", "(twitter[0-9a-z_ .\\-,]{0,25})(=|>|:=|\\|\\|:|<=|=>|:).{0,5}['\\\"](\\s[a-f0-9]{35,44})['\\\"]")
            ,new CodetyMatchingRule(4,"atlassian-token", "(atlassian[0-9a-z_ .\\-,]{0,25})(=|>|:=|\\|\\|:|<=|=>|:).{0,5}['\\\"](\\s[0-9a-z]{24})['\\\"]")
            ,new CodetyMatchingRule(4,"bitbucket-token", "(bitbucket[0-9a-z_ .\\-,]{0,25})(=|>|:=|\\|\\|:|<=|=>|:).{0,5}['\\\"](\\s[0-9a-z]{32})['\\\"]")
            ,new CodetyMatchingRule(4,"bitbucket-secret", "(bitbucket[0-9a-z_ .\\-,]{0,25})(=|>|:=|\\|\\|:|<=|=>|:).{0,5}['\\\"](\\s[0-9a-z_\\-]{64})['\\\"]")
            ,new CodetyMatchingRule(4,"databricks-api-token", "dapi[a-h0-9]{32}")
            ,new CodetyMatchingRule(4,"dropbox-api-secret", "(dropbox[0-9a-z_ .\\-,]{0,25})(=|>|:=|\\|\\|:|<=|=>|:).{0,5}['\\\"]([0-9a-z]{15})['\\\"]")
            ,new CodetyMatchingRule(4,"hashicorp-api-token", "['\\\"](?i)[0-9a-z]{14}\\.atlasv1\\.[0-9a-z\\-_=]{60,70}['\\\"]")
            ,new CodetyMatchingRule(4,"jwt-token", "ey[a-z0-9A-Z]{17,}\\.ey[a-z0-9A-Z\\/\\\\_-]{17,}\\.(?:[a-z0-9A-Z\\/\\\\_-]{10,}={0,2})?")
            ,new CodetyMatchingRule(4,"new-relic-user-api-key", "['\\\"](NRAK-[0-9A-Z]{27})['\\\"]")
            ,new CodetyMatchingRule(5,"npm-access-token", "['\\\"](npm_(?i)[0-9a-z]{36})['\\\"]")
            ,new CodetyMatchingRule(5,"dockerconfig-secret", "pul-[a-f0-9]{40}")
    );

}
