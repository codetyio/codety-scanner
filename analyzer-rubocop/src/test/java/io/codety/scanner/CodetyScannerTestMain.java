package io.codety.scanner;

import io.codety.scanner.util.CodetyConstant;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(CodetyConstant.BASE_PACKAGE) //fixing the NoSuchBeanDefinitionException
//@Profile("!dev")
public class CodetyScannerTestMain implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {

    }
}