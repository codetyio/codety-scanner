package io.codety.scanner.util;

import java.io.PrintStream;

public class CodetyConsoleLogger {
//    public static Logger logger = LoggerFactory.getLogger(CodetyConsoleLogger.class);

    private static boolean printDebugInfo = false;
    private static final String infoPrevix = "[INFO]";
    private static final String debugPrefix = "[DEBUG]";
    private static PrintStream defaultLogOutputStream = System.out;

    public static void setPrintDebugInfo(boolean printDebugInfo) {
        CodetyConsoleLogger.printDebugInfo = printDebugInfo;
    }

    public static void info(String str){
        defaultLogOutputStream.println(infoPrevix + str);
    }
    public static void info(String str, Throwable e){
        defaultLogOutputStream.println(infoPrevix + str + ", errorMessage: " + e.getMessage());
    }

    public static void debug(String message) {
        if(!isDebugMode()){
            return;
        }
        defaultLogOutputStream.println(debugPrefix + message);
    }
    public static void debug(String message, Throwable e) {
        if(!isDebugMode()){
            return;
        }
        debug(message);
        debug(e);
    }

    public static boolean isDebugMode(){
        return printDebugInfo;
    }

    public static void debug(Throwable e) {
        if(isDebugMode()) {
            e.printStackTrace(defaultLogOutputStream);
        }
    }
}
