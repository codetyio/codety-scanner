package io.codety.scanner.service;

import io.codety.scanner.prework.dto.GitArtifactChangeLineSegment;
import io.codety.scanner.prework.dto.GitDiffFileChange;
import io.codety.scanner.prework.dto.GitFileChangeList;
import io.codety.scanner.util.CodetyConsoleLogger;
import io.codety.scanner.util.CodetyConstant;
import io.codety.scanner.util.RuntimeExecUtil;
import org.springframework.stereotype.Component;

@Component
public class CodeChangeDiffAnalyzer {
    private static final String INFO_startGitDiff = "Start calculating file changes ";
    private static final String INFO_failedGitDiff = "Failed to run git diff between the current branch and ";
    public GitFileChangeList getGitDiffFileChanges(String targetGitBranchRef, String localGitRepoPath) {

        CodetyConsoleLogger.debug(INFO_startGitDiff);
        try {
            String gitDiffCommandResponse = getGitDiff(targetGitBranchRef, localGitRepoPath);
            GitFileChangeList changeList = convertGitDiffPatch(gitDiffCommandResponse);
            return changeList;
        } catch (Exception e) {

            CodetyConsoleLogger.info(INFO_failedGitDiff + targetGitBranchRef);
        }
        return new GitFileChangeList(false);

    }

    public GitFileChangeList convertGitDiffPatch(String gitDiffPayload) {
        if(gitDiffPayload.equals("")){
            return new GitFileChangeList(false); //When running inside the default/main brancn.
        }
        String[] split = gitDiffPayload.split("\\n");
        GitFileChangeList gitFileChangeList = parseDiffPayload(split);
        return gitFileChangeList;
    }

    public GitFileChangeList parseDiffPayload(String[] split) {
        CodetyConsoleLogger.debug("Start parsing payload. ");
        GitFileChangeList gitFileChangeList = new GitFileChangeList(true);
        /*
======= code example: =========
diff --git a/complete/src/main/java/com/example/springboot/Application.java b/complete/src/main/java/com/example/springboot/Application.java
index c2330e7..687a61f 100644
--- a/complete/src/main/java/com/example/springboot/Application.java
+++ b/complete/src/main/java/com/example/springboot/Application.java
@@ -10,7 +10,7 @@ import org.springframework.context.annotation.Bean;
        * */
        String filePath = null;
        GitDiffFileChange change = null;

        Result result = null;//getResult(split, startIndex);

        GitArtifactChangeLineSegment segment = null;
        int skippingNumberFromDiffMeta = 0; // measure the distance between the @@ line and the first + line.
        for (int i = 0; i < split.length; i++) {
            String currentLine = split[i];
            if (currentLine.startsWith("@@")) {

                result = getResult(split, i);
                skippingNumberFromDiffMeta = 0;
                segment = null; // reset segment.
                continue;
            } else if (currentLine.startsWith("diff")) {
                filePath = null;//skip two lines.
                for (int j = i; j < split.length; j++) {
                    String targetNewFileChangeMetaIdentifier = "+++ b/";
                    if (split[j].startsWith(targetNewFileChangeMetaIdentifier)) {
                        filePath = split[j].substring(targetNewFileChangeMetaIdentifier.length());
                        break;
                    }
                }
                change = new GitDiffFileChange(filePath);
                gitFileChangeList.putFileChangeRecord(filePath, change);
                for (; i < split.length; i++) {
                    if (split[i].startsWith("@@")) {
                        i--;
                        break;
                    }
                }
                continue;
            }

            char firstCharInCurrentLine = currentLine.charAt(0);
            if (firstCharInCurrentLine != '-') {
                skippingNumberFromDiffMeta++;
            }
            if (firstCharInCurrentLine == '+') {
                if (segment == null) {
                    int realStartPosition = result.destinationChangeStart() + skippingNumberFromDiffMeta - 1;
                    segment = new GitArtifactChangeLineSegment(realStartPosition, realStartPosition);
                    change.getChangeLineSegmentList().add(segment);
                } else {
                    segment.setEndLineInclusive(segment.getEndLineInclusive() + 1);
                }
            } else {
                segment = null;
            }
        }

        gitFileChangeList.removeEmptySegments();
        CodetyConsoleLogger.debug("Detected the number of changed files: " + gitFileChangeList.getGitDiffChangeMap().size());
        return gitFileChangeList;
    }

    private static Result getResult(String[] split, int startIndex) {
        String metaLine = split[startIndex]; // example: @@ -14,22 +14,7 @@ public class Application {
        String diffMeta = metaLine.split("@@")[1]; //example: -14,22 +14,7
        String[] sourceAndDestinationMeta = diffMeta.trim().split("\\s");
        String[] destinationMeta = sourceAndDestinationMeta[1].split(",");
        int destinationChangeStart = Integer.valueOf(destinationMeta[0].replace("+", ""));// example: 14
        int destinationNumOfLineOfChanges = destinationMeta.length == 1 ? 1 : Integer.valueOf(destinationMeta[1].replace("+", ""));// example: 7
        Result result = new Result(destinationChangeStart, destinationNumOfLineOfChanges, startIndex);
        return result;
    }

    private record Result(int destinationChangeStart, int destinationNumOfLineOfChanges, int startIndex) {
    }


    public String getGitDiff(String targetGitBranchRef, String path) throws Exception {

        //prepare the git repo, remove the warnings, fetch the remote branches.
        String[] strings = {"bash", "-c", "git config --global --add safe.directory '"+path+"'"};
        RuntimeExecUtil.exec(strings, null, 3, false, null);
        String[] strings1 = {"bash", "-c", "git fetch"};
        RuntimeExecUtil.exec(strings1, path, 60, false, null);

        String git = CodetyConstant.COMMAND_GIT;
        RuntimeExecUtil.RuntimeExecResult runtimeExecResult = null;
        if(targetGitBranchRef != null){
            String[] command = {"bash", "-c", "git diff origin/" + targetGitBranchRef};
            runtimeExecResult = RuntimeExecUtil.exec(command, path, 5, false, null);
        }else{
            //get the default remote branch name, which will be used for run `git diff`
            String[] fetchRemoteBeranchCommand = {"bash", "-c", "git branch --remotes"};
            RuntimeExecUtil.RuntimeExecResult fetchRemoteBranch = RuntimeExecUtil.exec(fetchRemoteBeranchCommand, path, 10, false, null);
            String targetBranch = fetchRemoteBranch.getSuccessOutput().split("\n")[0].trim(); // get something like: origin/main
            String[] split = targetBranch.split("->"); //  origin/HEAD -> origin/main
            targetBranch = split[split.length-1];
            String[] command = {"bash", "-c", "git diff " + targetBranch};
            runtimeExecResult = RuntimeExecUtil.exec(command, path, 5, false, null);
        }

        return runtimeExecResult.getSuccessOutput();

    }
}
