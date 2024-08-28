package io.codety.scanner.analyzer.eslint;

import io.codety.scanner.analyzer.eslint.dto.EslintResults;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class EslintJsonWithMetadataDeserializerTest {

    @Test
    void testDeserialize() throws IOException {
        String s = Files.readString(Path.of(this.getClass().getResource("/eslint/output/eslint-json-with-metadata.json").getFile()).toAbsolutePath());
        EslintResults deserialize = EslintJsonWithMetadataDeserializer.deserialize(s);
        Assertions.assertNotNull(deserialize);
        Assertions.assertTrue(deserialize.getResults().length > 1);
    }

    @Test
    void testDeserializeHtml() throws IOException {
        String s = Files.readString(Path.of(this.getClass().getResource("/eslint/output/eslint9-html-output.json").getFile()).toAbsolutePath());
        EslintResults deserialize = EslintJsonWithMetadataDeserializer.deserialize(s);
        Assertions.assertNotNull(deserialize);
        Assertions.assertTrue(deserialize.getResults().length >= 1);
        Assertions.assertTrue(deserialize.getResults()[0].getMessages().length >=4);
    }
}
