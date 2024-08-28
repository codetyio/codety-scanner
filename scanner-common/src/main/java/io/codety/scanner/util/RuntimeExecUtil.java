package io.codety.scanner.util;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class RuntimeExecUtil {

    private static final String INFO_executing = "Executing ";
    private static final String INFO_underPath = " under path: ";
    private static final String INFO_result = "Result: ";
    private static final String INFO_responseOutputLength = "Result output length: ";
    public static RuntimeExecResult exec(String[] command, String executionPath, int timeoutSeconds, boolean printDebugInfo, Map<String, String> additionalEnv) throws Exception {
        RuntimeExecResult runtimeExecResult = new RuntimeExecResult();

        File file = executionPath == null ? null : Path.of(executionPath).toAbsolutePath().toFile();

        String[] envp = null;
        if(additionalEnv!=null && !additionalEnv.isEmpty()) {
            List<String> env = new ArrayList<>();
            Map<String, String> getenv = System.getenv();
            for(String key : getenv.keySet()){
                env.add(key + "=" + getenv.get(key));
            }
            for(String key : additionalEnv.keySet()){
                env.add(key + "=" + additionalEnv.get(key));
            }
            envp = env.toArray(new String[0]);
        }

        CodetyConsoleLogger.debug(INFO_executing + String.join(" ", command) + INFO_underPath + file );
        Process exec = Runtime.getRuntime().exec(command, envp, file);
        runtimeExecResult.setSuccessOutput(new String(exec.getInputStream().readAllBytes())); ;
        runtimeExecResult.setErrorOutput(new String(exec.getErrorStream().readAllBytes()));
        exec.waitFor(timeoutSeconds, TimeUnit.SECONDS);

        String successOutput = runtimeExecResult.getSuccessOutput();
        successOutput = successOutput == null || successOutput.isEmpty() ? "<EMPTY>" : successOutput;
        if(printDebugInfo) {
            CodetyConsoleLogger.debug(INFO_result + successOutput);
        }else{
            CodetyConsoleLogger.debug(INFO_responseOutputLength + successOutput.length());
        }
        return runtimeExecResult;

    }

    public static class RuntimeExecResult{
        String successOutput;
        String errorOutput;

        public String getSuccessOutput() {
            return successOutput;
        }

        public void setSuccessOutput(String successOutput) {
            this.successOutput = successOutput;
        }

        public String getErrorOutput() {
            return errorOutput;
        }

        public void setErrorOutput(String errorOutput) {
            this.errorOutput = errorOutput;
        }
    }
}
