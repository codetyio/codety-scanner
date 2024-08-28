package io.codety.scanner.analyzer.golangcilint;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public class GolangcilintModuleTest {

    @Test
    void testFindModules(){
        String string = Path.of("src","test","resources","go-module-structure-test").toAbsolutePath().toString();
        List<File> goModules = GolangcilintModuleUtil.findGoModules(string);
        Assertions.assertTrue(goModules.size() == 3);
    }

}
