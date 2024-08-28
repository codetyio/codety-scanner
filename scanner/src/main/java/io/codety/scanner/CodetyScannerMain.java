package io.codety.scanner;

import io.codety.scanner.service.ScannerService;
import io.codety.scanner.util.CodetyConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/*
*
* Keep the class name as it, or you need to change the proguard setting in gradle.build.
* */
@SpringBootApplication
@ComponentScan(CodetyConstant.BASE_PACKAGE) //fixing the NoSuchBeanDefinitionException
//@Profile("!dev")
public class CodetyScannerMain implements CommandLineRunner {

	@Autowired
	ScannerService scannerService;

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(CodetyScannerMain.class);
		springApplication.setBannerMode(Banner.Mode.OFF);
		springApplication.run(args).close();
	}

	@Override
	public void run(String... args) throws Exception {
		scannerService.process(args);

	}
}
