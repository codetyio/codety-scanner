package io.codety.scanner.analyzer.cppcheck.dto;

import java.util.ArrayList;
import java.util.List;

public class CppcheckCmdResult {

    List<CppcheckCmdIssue> cppcheckIssueList = new ArrayList();

    public List<CppcheckCmdIssue> getCppcheckIssueList() {
        return cppcheckIssueList;
    }

    public void setCppcheckIssueList(List<CppcheckCmdIssue> cppcheckIssueList) {
        this.cppcheckIssueList = cppcheckIssueList;
    }
}
