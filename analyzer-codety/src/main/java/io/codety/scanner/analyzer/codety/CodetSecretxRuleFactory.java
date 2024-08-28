package io.codety.scanner.analyzer.codety;

import io.codety.scanner.analyzer.codety.dto.CodetyRegexAnalyzerRule;

import java.util.ArrayList;
import java.util.List;

public class CodetSecretxRuleFactory {

    private static final String rules =
            "artifactory-api-token\t\t(?:\\s|=|:|\"|^)AKC[a-zA-Z0-9]{10,}\n" +
//            "authorization-basic\t\tbasic [a-zA-Z0-9_\\\\-:\\\\.=]+\n" +
//            "authorization-bearer\t\tbearer [a-zA-Z0-9_\\\\-:\\\\.=]+\n" +
                    "aws-access-key\t\t(A3T[A-Z0-9]|AKIA|AGPA|AIDA|AROA|AIPA|ANPA|ANVA|ASIA)[A-Z0-9]{16}\n" +
                    "aws-mws-key\t\tamzn\\.mws\\.[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}\n" +
                    "cloudinary-basic-auth\t\tcloudinary:\\/\\/[0-9]{15}:[0-9A-Za-z]+@[a-z]+\n" +
                    "github-access-token\t\t(ghu|ghs|ghr|gho|ghp)_[0-9a-zA-Z]{36}\n" +
                    "github-fine-grained-pat-token\t\tgithub_pat_[a-zA-Z0-9]{22}_[a-zA-Z0-9]{59}\n" +
                    "gitlab-pat-token\t\tglpat-[0-9a-zA-Z\\-\\_]{20}\n" +
                    "pgp-key\t\t^(-----BEGIN PGP PRIVATE KEY BLOCK-----)\n" +
                    "ssl-certificate\t\t^-----BEGIN CERTIFICATE-----\n" +
                    "putty-ssh-rsa-key\t\t^PuTTY-User-Key-File-2: ssh-rsa\\s*Encryption: none(?:.|\\s?)*?Private-MAC:$\n" +
                    "putty-ssh-dsa-key\t\t^PuTTY-User-Key-File-2: ssh-dss\\s*Encryption: none(?:.|\\s?)*?Private-MAC:$\n" +
                    "ecdsa-private-key\t\t^-----BEGIN ECDSA PRIVATE KEY-----\\s.*,ENCRYPTED(?:.|\\s)+?-----END ECDSA PRIVATE KEY-----$\n" +
                    "shopify-api-token\t\tshp(ss|at|ca|pa)_[a-fA-F0-9]{32}\n" +
                    "slack-token\t\txox[baprs]-([0-9a-zA-Z]{10,48})\n" +
                    "slack-web-token\t\thttps://hooks.slack.com/services/T[a-zA-Z0-9_]{10}/B[a-zA-Z0-9_]{10}/[a-zA-Z0-9_]{24}\n" +
                    "stripe-api-key\t\t(pk|sk|rk)_(test|live)_[A-Za-z0-9]+\n" +
                    "square-access-token\t\tsqOatp-[0-9A-Za-z\\\\-_]{22}\n" +
                    "square-oauth-secret\t\tsq0csp-[ 0-9A-Za-z\\\\-_]{43}\n" +
                    "pypi-token\t\tpypi-AgEIcHlwaS5vcmc[A-Za-z0-9\\-_]{50,1000}\n" +
                    "gcp-token\t\t\\\"type\\\": \\\"service_account\\\"\n" +
                    "gcp-api-key\t\t(?i)(google|gcp|youtube|drive|yt)(.{0,20})?['\\\"][AIza[0-9a-z\\\\-_]{35}]['\\\"]\n" +
                    "heroku-token\t\t(heroku[a-z0-9_ .\\-,]{0,25})(=|>|:=|\\|\\|:|<=|=>|:).{0,5}['\\\"]([0-9A-F]{8}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{12})['\\\"]\n" +
                    "twilio-token\t\tSK[0-9a-fA-F]{32}\n" +
                    "facebook-token\t\t(facebook[a-z0-9_ .\\-,]{0,25})(=|>|:=|\\|\\|:|<=|=>|:).{0,5}['\\\"](\\s[a-f0-9]{32})['\\\"]\n" +
                    "facebook-access-token\t\tEAACEdEose0cBA[0-9A-Za-z]+\n" +
                    "facebook-client-id\t\t(?i)(facebook|fb)(.{0,20})?['\\\"][0-9]{13,17}\n" +
                    "facebook-oauth\t\t[f|F][a|A][c|C][e|E][b|B][o|O][o|O][k|K].*['|\\\"][0-9a-f]{32}['|\\\"]\n" +
                    "facebook-secret-key\t\t(?i)(facebook|fb)(.{0,20})?(?-i)['\\\"][0-9a-f]{32}\n" +
                    "google-api-key\t\tAIza[0-9A-Za-z\\\\-_]{35}\n" +
                    "twitter-client-id\t\t(?i)twitter(.{0,20})?['\\\"][0-9a-z]{18,25}\n" +
                    "twitter-oauth\t\t[t|T][w|W][i|I][t|T][t|T][e|E][r|R].{0,30}['\\\"\\\\s][0-9a-zA-Z]{35,44}['\\\"\\\\s]\n" +
                    "twitter-secret-key\t\t(?i)twitter(.{0,20})?['\\\"][0-9a-z]{35,44}\n" +
                    "vault-token\t\t[sb]\\.[a-zA-Z0-9]{24}\n" +
                    "adobe-token\t\t(adobe[a-z0-9_ .\\-,]{0,25})(=|>|:=|\\|\\|:|<=|=>|:).{0,5}['\\\"](\\s[a-f0-9]{32})['\\\"]\n" +
                    "adobe-secret\t\t(p8e-)(?i)[a-z0-9]{32}\n" +
                    "atlassian-token\t\t(atlassian[a-z0-9_ .\\-,]{0,25})(=|>|:=|\\|\\|:|<=|=>|:).{0,5}['\\\"](\\s[a-z0-9]{24})['\\\"]\n" +
                    "bitbucket-token\t\t(bitbucket[a-z0-9_ .\\-,]{0,25})(=|>|:=|\\|\\|:|<=|=>|:).{0,5}['\\\"](\\s[a-z0-9]{32})['\\\"]\n" +
                    "bitbucket-secret\t\t(bitbucket[a-z0-9_ .\\-,]{0,25})(=|>|:=|\\|\\|:|<=|=>|:).{0,5}['\\\"](\\s[a-z0-9_\\-]{64})['\\\"]\n" +
                    "databricks-api-token\t\tdapi[a-h0-9]{32}\n" +
                    "discord-api-token\t\t(discord[a-z0-9_ .\\-,]{0,25})(=|>|:=|\\|\\|:|<=|=>|:).{0,5}['\\\"](\\s[a-h0-9]{64})['\\\"]\n" +
                    "discord-client-id\t\t(discord[a-z0-9_ .\\-,]{0,25})(=|>|:=|\\|\\|:|<=|=>|:).{0,5}['\\\"](\\s[0-9]{18})['\\\"]\n" +
                    "discord-client-secret\t\t(discord[a-z0-9_ .\\-,]{0,25})(=|>|:=|\\|\\|:|<=|=>|:).{0,5}['\\\"](\\s[a-z0-9=_\\-]{32})['\\\"]\n" +
                    "dropbox-api-secret\t\t(dropbox[a-z0-9_ .\\-,]{0,25})(=|>|:=|\\|\\|:|<=|=>|:).{0,5}['\\\"]([a-z0-9]{15})['\\\"]\n" +
                    "dropbox-short-lived-api-token\t\t(dropbox[a-z0-9_ .\\-,]{0,25})(=|>|:=|\\|\\|:|<=|=>|:).{0,5}['\\\"](sl\\.[a-z0-9\\-=_]{135})['\\\"]\n" +
                    "dropbox-long-lived-api-token\t\t(dropbox[a-z0-9_ .\\-,]{0,25})(=|>|:=|\\|\\|:|<=|=>|:).{0,5}['\\\"][a-z0-9]{11}(AAAAAAAAAA)[a-z0-9\\-_=]{43}['\\\"]\n" +
                    "hashicorp-tf-api-token\t\t['\\\"](?i)[a-z0-9]{14}\\.atlasv1\\.[a-z0-9\\-_=]{60,70}['\\\"]\n" +
                    "jwt-token\t\tey[a-zA-Z0-9]{17,}\\.ey[a-zA-Z0-9\\/\\\\_-]{17,}\\.(?:[a-zA-Z0-9\\/\\\\_-]{10,}={0,2})?\n" +
                    "newrelic-user-api-key\t\t['\\\"](NRAK-[A-Z0-9]{27})['\\\"]\n" +
                    "npm-access-token\t\t['\\\"](npm_(?i)[a-z0-9]{36})['\\\"]\n" +
                    "pulumi-api-token\t\tpul-[a-f0-9]{40}\n" +
                    "dockerconfig-secret\t\tpul-[a-f0-9]{40}";

    private static List<CodetyRegexAnalyzerRule> cachedRules = null;
    public static List<CodetyRegexAnalyzerRule> getDefaultCodetyRules(){
        if(cachedRules != null){
            return cachedRules;
        }

        List<CodetyRegexAnalyzerRule> result = new ArrayList<>();
        String[] ruleStr = rules.split("\n");
        for(String rule : ruleStr){
            String[] split = rule.split("\t\t");
            String ruleId = split[0];
            String regex = split[1];
            CodetyRegexAnalyzerRule regexAnalyzerRule = new CodetyRegexAnalyzerRule();
            regexAnalyzerRule.setRegex(regex);
            regexAnalyzerRule.setIssueCode(ruleId);
            regexAnalyzerRule.setDescription("Detected potential exposure of sensitive information");
            regexAnalyzerRule.setPriority(4);
            regexAnalyzerRule.setIssueCategory("security");
            result.add(regexAnalyzerRule);
        }
        cachedRules = result;
        return cachedRules;
    }




}
