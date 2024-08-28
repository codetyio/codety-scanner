package io.codety.scanner.service;

import io.codety.scanner.prework.dto.GitArtifactChangeLineSegment;
import io.codety.scanner.prework.dto.GitDiffFileChange;
import io.codety.scanner.prework.dto.GitFileChangeList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@SpringBootTest
@ActiveProfiles(value = "dev")
public class CodeChangeDiffAnalyzerTest {

    @Autowired
    CodeChangeDiffAnalyzer codeChangeDiffAnalyzer;


    @Test
    public void testGitCommand(){
        String targetBranch = "main";
        GitFileChangeList gitFileChangeList = codeChangeDiffAnalyzer.getGitDiffFileChanges(targetBranch, null);
        Assertions.assertNotNull(gitFileChangeList);
    }
    @Test
    public void testGitCommandFail(){
        String targetBranch = "random_non_existing_branch";
        GitFileChangeList gitFileChangeList = codeChangeDiffAnalyzer.getGitDiffFileChanges(targetBranch, null);
        Assertions.assertFalse(gitFileChangeList.isValid());
    }

    @Test
    public void detectGitDiff() throws IOException {
        Path path = Path.of(this.getClass().getResource("/github/git_diff_test/git_diff_long.txt").getPath());
        String diffPayload = Files.readString(path);

        GitFileChangeList gitFileChangeList = codeChangeDiffAnalyzer.convertGitDiffPatch(diffPayload);
        Assertions.assertNotNull(gitFileChangeList);;

        String key = "complete/src/main/java/com/example/springboot/Application.java";
        GitDiffFileChange change = gitFileChangeList.getGitDiffChangeMap().get(key);
        List<GitArtifactChangeLineSegment> changeLineSegmentList = change.getChangeLineSegmentList();
        Assertions.assertTrue(changeLineSegmentList.size() == 4);
    }

    @Test
    public void testGitDiffDelete() throws IOException {
        Path path = Path.of(this.getClass().getResource("/github/git_diff_test/git_diff_delete.txt").getPath());
        String diffPayload = Files.readString(path);

        GitFileChangeList gitFileChangeList = codeChangeDiffAnalyzer.convertGitDiffPatch(diffPayload);
        Assertions.assertNotNull(gitFileChangeList);

        Assertions.assertTrue(gitFileChangeList.getGitDiffChangeMap().size() == 1);

        String key = "complete/src/main/java/com/example/springboot/Application.java";
        GitDiffFileChange change = gitFileChangeList.getGitDiffChangeMap().get(key);
        List<GitArtifactChangeLineSegment> changeLineSegmentList = change.getChangeLineSegmentList();
        Assertions.assertTrue(changeLineSegmentList.size() == 1);
    }

    @Test
    public void testGitDiffShort() throws IOException {
        Path path = Path.of(this.getClass().getResource("/github/git_diff_test/git_diff_short.txt").getPath());
        String diffPayload = Files.readString(path);
        GitFileChangeList gitFileChangeList = codeChangeDiffAnalyzer.convertGitDiffPatch(diffPayload);
        Assertions.assertNotNull(gitFileChangeList);

        String key = "complete/src/main/java/com/example/springboot/Application.java";
        GitDiffFileChange change = gitFileChangeList.getGitDiffChangeMap().get(key);
        List<GitArtifactChangeLineSegment> changeLineSegmentList = change.getChangeLineSegmentList();
        Assertions.assertTrue(changeLineSegmentList.size() == 1);

        Assertions.assertTrue(changeLineSegmentList.get(0).getStartLineInclusive() == 13 );
        Assertions.assertTrue(changeLineSegmentList.get(0).getEndLineInclusive() == 13 );
    }

}
