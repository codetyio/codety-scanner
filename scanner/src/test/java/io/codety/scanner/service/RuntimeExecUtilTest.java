package io.codety.scanner.service;

import io.codety.scanner.util.RuntimeExecUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RuntimeExecUtilTest {

    @Test
    public void testSuccess(){
        try {
            RuntimeExecUtil.RuntimeExecResult runtimeExecResult = RuntimeExecUtil.exec(new String[]{"java", "--version"}, null, 1000, true, null);
            Assertions.assertTrue(runtimeExecResult.getSuccessOutput().contains("HotSpot"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void testFailure() throws InterruptedException {
        Exception exception = null;
        try {
            RuntimeExecUtil.RuntimeExecResult runtimeExecResult = RuntimeExecUtil.exec(new String[]{"blablablablablabla", "--abcdefg"}, null, 1000, true, null);
        } catch (Exception e) {
            exception = e;
        }
        Assertions.assertNotNull(exception);

    }
}
