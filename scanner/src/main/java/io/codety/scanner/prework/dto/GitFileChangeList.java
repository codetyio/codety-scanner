package io.codety.scanner.prework.dto;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GitFileChangeList {
    boolean valid = false;

    Map<String, GitDiffFileChange> gitDiffChangeMap = new HashMap();

    public GitFileChangeList(boolean valid) {
        this.valid = valid;
    }

    public void putFileChangeRecord(String filePath, GitDiffFileChange change) {
        gitDiffChangeMap.put(filePath, change);
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public GitDiffFileChange getFileChangeByFilePath(String filePath) {
        return gitDiffChangeMap.get(filePath);
    }

    public boolean containsCode(String path) {
        return gitDiffChangeMap.containsKey(path);
    }

    public GitDiffFileChange getChange(String path) {
        return gitDiffChangeMap.get(path);
    }

    public Map<String, GitDiffFileChange> getGitDiffChangeMap() {
        return gitDiffChangeMap;
    }

    public void setGitDiffChangeMap(Map<String, GitDiffFileChange> gitDiffChangeMap) {
        this.gitDiffChangeMap = gitDiffChangeMap;
    }

    public void removeEmptySegments() {

        Iterator<String> iterator = gitDiffChangeMap.keySet().iterator();
        while (iterator.hasNext()) {
            GitDiffFileChange change1 = gitDiffChangeMap.get(iterator.next());
            if (change1.getChangeLineSegmentList().isEmpty()) {
                iterator.remove();
            }
        }

    }
}
