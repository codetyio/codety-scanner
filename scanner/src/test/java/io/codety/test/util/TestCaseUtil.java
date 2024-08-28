package io.codety.test.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class TestCaseUtil {

    public static Map<String, String> createEnvironmentVariableMapsForTestCase() throws IOException {
        String s = Files.readString(Path.of(TestCaseUtil.class.getResource("/container-env/github_actions_predefined_env.txt").getFile()).toAbsolutePath());
        Map<String, String> env = new HashMap();
        for(String e : s.split("\n")){
            String[] split = e.split("=");
            if(split.length == 2)
                env.put(split[0], split[1]);
        }
        ;
        return env;
    }
}
