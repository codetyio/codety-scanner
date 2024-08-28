package io.codety.scanner.prework.dto;

import java.util.ArrayList;
import java.util.List;

public class GitDiffFileChange {

    String relativeFilePath;

    public GitDiffFileChange(String relativeFilePath) {
        this.relativeFilePath = relativeFilePath;
    }

    public GitDiffFileChange(String relativeFilePath, List<GitArtifactChangeLineSegment> changeLineSegmentList) {
        this.relativeFilePath = relativeFilePath;
        this.changeLineSegmentList = changeLineSegmentList;
    }

    List<GitArtifactChangeLineSegment> changeLineSegmentList = new ArrayList<>();

    public List<GitArtifactChangeLineSegment> getChangeLineSegmentList() {
        return changeLineSegmentList;
    }

    public void setChangeLineSegmentList(List<GitArtifactChangeLineSegment> changeLineSegmentList) {
        this.changeLineSegmentList = changeLineSegmentList;
    }


    public boolean includeChange(Integer startLineNumber) {
        if(changeLineSegmentList == null || changeLineSegmentList.isEmpty() || startLineNumber == null){
            return false;
        }
        for(GitArtifactChangeLineSegment segment : changeLineSegmentList){
            if(startLineNumber >= segment.getStartLineInclusive() && startLineNumber <= segment.getEndLineInclusive() ){
                return true;
            }
        }

        return false;
    }

    public void addChangeSegment(GitArtifactChangeLineSegment segment) {
        changeLineSegmentList.add(segment);
    }
}
