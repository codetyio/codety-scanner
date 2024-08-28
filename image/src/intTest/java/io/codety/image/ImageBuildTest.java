package io.codety.image;

import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.output.ToStringConsumer;
import org.testcontainers.images.builder.ImageFromDockerfile;

import java.nio.file.Path;

public class ImageBuildTest {
    ToStringConsumer consumer = new ToStringConsumer();
    private static Logger logger = LoggerFactory.getLogger(ImageBuildTest.class);

    //platform=linux/amd64
//    @Rule
//    public GenericContainer dslContainer = new GenericContainer(
//            new ImageFromDockerfile()
//                    .withBuildArg("platform", "linux/amd64") //force to use amd64 platform
//                    .withDockerfile(Path.of("./Dockerfile"))).withLogConsumer(new Slf4jLogConsumer(logger)).withLogConsumer(consumer);

    @Test
    public void testBuild() throws InterruptedException {
        System.out.println(Path.of("Dockerfile").toFile().getAbsoluteFile());
        logger.info("blabla");
        GenericContainer dslContainer = new GenericContainer(
                new ImageFromDockerfile()
                        .withBuildArg("platform", "linux/amd64") //force to use amd64 platform
                        .withDockerfile(Path.of("./Dockerfile"))).withLogConsumer(new Slf4jLogConsumer(logger)).withLogConsumer(consumer);


        dslContainer.withEnv("CODETY_TOKEN", "blabla").start();

        String utf8String = consumer.toUtf8String();


    }

}
