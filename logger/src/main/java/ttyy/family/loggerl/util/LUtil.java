package ttyy.family.loggerl.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Author: hujinqi
 * Date  : 2016-08-30
 * Description:
 */
public class LUtil {

    public static String format(Throwable throwable){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        return sw.toString();
    }

    /**
     * 打印当前方法
     */
    public static String logStackTraceInfo(int stackIndex) {

        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        stackIndex = stackIndex > trace.length ? trace.length - 1 : stackIndex;

        StringBuilder methodLog = new StringBuilder();

        String clzName = trace[stackIndex].getClassName();
        int lastIndex = clzName.lastIndexOf(".");
        clzName = clzName.substring(lastIndex + 1);
        methodLog.append(clzName)
                .append(".")
                .append(trace[stackIndex].getMethodName())
                .append("(")
                .append(trace[stackIndex].getFileName())
                .append(":")
                .append(trace[stackIndex].getLineNumber())
                .append(")");

        return methodLog.toString();
    }

}
