package io.codety.scanner.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;

@SpringBootTest
@ActiveProfiles(value = "dev")
class RunApplicationIntegrationTest {
    @Autowired
    ScannerService scannerService;

    @Value("${codety.analyzer.java.pmd.path}")
    String pmdPath;

    @Test
    void whenContextLoads_thenRunnersRun() throws Exception {
        String[] inputs = new String[]{"./"};

//Set system variable from your IDE's test case configuration.
//        getenv().put("CODETY_RUNNER_DEBUG", "1");
//        getenv().put("CODETY_TOKEN", "273fe3ff68264c79b3bfacaf30744e10ec16406655674e3899717f0ae901f76e");
//        getenv().put("CODETY_HOST", "http://localhost:8081");
//        getenv().put("CODETY_CONNECTION_TIMEOUT", "60");
        scannerService.process(inputs);

//        verify(applicationRunnerTaskExecutor, times(1)).run(any());
//        verify(commandLineTaskExecutor, times(1)).run(any());
    }

    @Test
    public void validPmdPath(){
        File resourcesDirectory = new File(pmdPath);
        Assertions.assertTrue(resourcesDirectory.exists());
    }
}
