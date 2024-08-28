package io.codety.scanner.reporter.sarif;

import com.contrastsecurity.sarif.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.codety.scanner.reporter.IssueToBulletPointTextGenerator;
import io.codety.scanner.reporter.ResultReporter;
import io.codety.scanner.reporter.dto.CodeAnalysisIssueDto;
import io.codety.scanner.reporter.dto.CodeAnalysisResultDto;
import io.codety.scanner.reporter.dto.CodeAnalysisResultSetDto;
import io.codety.scanner.service.dto.AnalyzerRequest;
import io.codety.scanner.util.CodetyConsoleLogger;
import io.codety.scanner.util.JsonFactoryUtil;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.lang.Exception;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Service
public class SarifResultReporter implements ResultReporter {

    public static final String sarifFileName = "codety-scanning-result.sarif";;
    @Override
    public void deliverResult(AnalyzerRequest analyzerRequest, CodeAnalysisResultSetDto codeAnalysisResultSetDto) {
//        if(analyzerRequest.getExternalPullRequestId() == null){
//            CodetyConsoleLogger.debug("Skip Sarif result processing due to the pull request ID is missing");
//            return;
//        }

        try {
            SarifSchema210 sarif = new SarifSchema210();
            sarif.setVersion(SarifSchema210.Version._2_1_0);
            sarif.setRuns(new ArrayList<>());
            sarif.set$schema(URI.create("https://json.schemastore.org/sarif-2.1.0.json"));






            List<CodeAnalysisResultDto> codeAnalysisResultDtoList = codeAnalysisResultSetDto.getCodeAnalysisResultDtoList();


            if(codeAnalysisResultDtoList == null || codeAnalysisResultDtoList.isEmpty()){
                return;
            }

            for(CodeAnalysisResultDto resultDto : codeAnalysisResultDtoList){

                Map<String, Integer> issueRuleIndexCounterMap = new HashMap();

                Run run = new Run();
                sarif.getRuns().add(run);
                Tool tool = new Tool();
                ToolComponent driver = new ToolComponent();
                driver.setName("Codety Scanner - " + resultDto.getDisplayTitle());
                HashSet<ReportingDescriptor> rules = new HashSet<>();

                driver.setRules(rules);
                tool.setDriver(driver);
                run.setTool(tool);

                run.setResults(new ArrayList<>());

                Map<String, List<CodeAnalysisIssueDto>> issuesByFile = resultDto.getIssuesByFile();
                if(issuesByFile == null){
                    continue;
                }

                for(String filePath : issuesByFile.keySet()){
                    List<CodeAnalysisIssueDto> codeAnalysisIssueDtos = issuesByFile.get(filePath);
                    if(codeAnalysisIssueDtos == null){
                        continue;
                    }


                    for(CodeAnalysisIssueDto issueDto : codeAnalysisIssueDtos){
                        Result runIssueResult = new Result();
                        Message message = new Message();

                        StringBuilder sb = new StringBuilder();
                        IssueToBulletPointTextGenerator.extracted(issueDto, sb);
                        message.setText(sb.toString());

                        String key = issueDto.getIssueCode();
                        if(!issueRuleIndexCounterMap.containsKey(key)){
                            ReportingDescriptor reportingDescriptor = convertReportingDescriptor(issueDto);
                            rules.add(reportingDescriptor);
                            issueRuleIndexCounterMap.put(key, 1);
                        }
                        runIssueResult.setMessage(message);
                        runIssueResult.setRuleId(issueDto.getIssueCode());
                        runIssueResult.setLocations(new ArrayList<>());
                        Location location = new Location();
                        PhysicalLocation physicalLocation = new PhysicalLocation();
                        ArtifactLocation artifactLocation = new ArtifactLocation();
                        artifactLocation.setUri(filePath);
                        physicalLocation.setArtifactLocation(artifactLocation);

                        Region region = new Region();
                        region.setStartLine(issueDto.getStartLineNumber());
                        region.setEndLine(issueDto.getEndLineNumber());
                        region.setStartColumn(1);
                        region.setEndColumn(1);
                        physicalLocation.setRegion(region);

                        location.setPhysicalLocation(physicalLocation);

                        runIssueResult.getLocations().add(location);

                        PartialFingerprints partialFingerprints = new PartialFingerprints();
//                        partialFingerprints.setAdditionalProperty("primaryLocationLineHash", UUID.randomUUID().toString());
                        runIssueResult.setPartialFingerprints(partialFingerprints);

                        run.getResults().add(runIssueResult);
                    }


                }


            }


            String sarifPayload = JsonFactoryUtil.objectMapper.writeValueAsString(sarif);
            String localGitRepoPath = analyzerRequest.getLocalGitRepoPath();

            File file = new File(localGitRepoPath + "/" + sarifFileName);
            if(file.exists()){
                file.delete();
            }
            file.getParentFile().mkdirs();
            file.createNewFile();
            Files.write(file.toPath(), sarifPayload.getBytes());
            CodetyConsoleLogger.info("Successfully generated to serialize sarif file " + file.toPath().toAbsolutePath() );
        } catch (JsonProcessingException e) {
            CodetyConsoleLogger.debug("Failed to serialize sarif file", e );
        } catch (IOException e) {
            CodetyConsoleLogger.debug("Failed write sarif file", e );
        } catch (Exception e) {
            CodetyConsoleLogger.debug("Failed process sarif file", e );
        }


    }

    private ReportingDescriptor convertReportingDescriptor(CodeAnalysisIssueDto issueDto) {
        ReportingDescriptor reportingDescriptor = new ReportingDescriptor();
        reportingDescriptor.setId(issueDto.getIssueCode());
        MultiformatMessageString shortDescription = new MultiformatMessageString();


        shortDescription.setText(issueDto.getDescription());

        reportingDescriptor.setShortDescription(shortDescription);
        reportingDescriptor.setFullDescription(shortDescription);
        reportingDescriptor.setHelp(shortDescription);
        PropertyBag properties = new PropertyBag();
        properties.setAdditionalProperty("category", issueDto.getIssueCategory());
        if(issueDto.getCweId()!=null){
            properties.setAdditionalProperty("cwe_id", issueDto.getCweId());
        }

        reportingDescriptor.setProperties(properties);

        return reportingDescriptor;
    }
}
