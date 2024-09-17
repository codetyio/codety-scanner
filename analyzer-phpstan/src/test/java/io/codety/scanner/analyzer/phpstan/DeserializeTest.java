package io.codety.scanner.analyzer.phpstan;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.codety.scanner.analyzer.phpstan.dto.PhpstanIssueDto;
import io.codety.scanner.analyzer.phpstan.dto.PhpstanRoot;
import io.codety.scanner.util.JsonFactoryUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DeserializeTest {

    @Test
    void testDeserialize() throws IOException {
        String file = this.getClass().getResource("/phpstan.example.json").getFile();
        String payload = Files.readString(Path.of(file));
        PhpstanRoot phpstanRoot = JsonFactoryUtil.objectMapper.readValue(payload, PhpstanRoot.class);
        Assertions.assertTrue(phpstanRoot.getFiles().size() == 2);
        for(String fileName : phpstanRoot.getFiles().keySet()){
            PhpstanIssueDto phpstanIssueDto = phpstanRoot.getFiles().get(fileName);
            Assertions.assertTrue(phpstanIssueDto.getErrors() > 0);
            Assertions.assertTrue(phpstanIssueDto.getMessages().size() > 0);
        }

    }
}
