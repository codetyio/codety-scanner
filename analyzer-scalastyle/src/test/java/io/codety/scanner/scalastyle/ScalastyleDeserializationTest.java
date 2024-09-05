package io.codety.scanner.scalastyle;

import io.codety.scanner.analyzer.scalastyle.dto.ScalastyleCheckstyle;
import io.codety.scanner.analyzer.scalastyle.dto.ScalastyleError;
import io.codety.scanner.analyzer.scalastyle.dto.ScalastyleFile;
import io.codety.scanner.util.XmlFactoryUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ScalastyleDeserializationTest {

    @Test
    void testDeserialize() throws IOException {
        URL resource = this.getClass().getResource("/scalastyle-issue-output-example.xml");
        Path path = Path.of(resource.getPath());
        String s = Files.readString(path);
        ScalastyleCheckstyle scalastyleCheckstyle = XmlFactoryUtil.xmlMapper.readValue(s, ScalastyleCheckstyle.class);
        Assertions.assertTrue(scalastyleCheckstyle.getFile().size() == 2);
        for(ScalastyleFile file : scalastyleCheckstyle.getFile()){
            Assertions.assertNotNull(file.getName());
            List<ScalastyleError> errors = file.getError();
            Assertions.assertTrue(errors.size() > 0);
            for(ScalastyleError error : errors){
                Assertions.assertTrue(error.getMessage().length() > 4);
                Assertions.assertTrue(error.getSource().length() > 4);
            }

        }
    }
}
