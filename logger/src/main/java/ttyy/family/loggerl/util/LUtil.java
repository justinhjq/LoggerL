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

    static int logStackTraceIndex(StackTraceElement[] trace){
        int start = 2;

        int firstLIndex = -1;
        for(; start<trace.length; start++){
            String clzName = trace[start].getClassName();
            int lastIndex = clzName.lastIndexOf(".");
            clzName = clzName.substring(lastIndex + 1);

            // 越过堆栈中L的信息
            if(clzName.equals("L")){
                firstLIndex = start;
            }else{
                if(firstLIndex != -1){
                    break;
                }
            }
        }
        return start;
    }

    static String logStackTrancInfo_real(StackTraceElement[] trace, int stackIndex){
        StringBuilder sb = new StringBuilder();

        String clzName = trace[stackIndex].getClassName();
        int lastIndex = clzName.lastIndexOf(".");
        clzName = clzName.substring(lastIndex + 1);
        sb.append(clzName)
                .append(".")
                .append(trace[stackIndex].getMethodName())
                .append("(")
                .append(trace[stackIndex].getFileName())
                .append(":")
                .append(trace[stackIndex].getLineNumber())
                .append(")");

        return sb.toString();
    }

    /**
     * 打印当前方法
     */
    public static String logStackTraceInfo() {

        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        int stackIndex = logStackTraceIndex(trace);

        StringBuilder methodLog = new StringBuilder();
        methodLog.append(logStackTrancInfo_real(trace, stackIndex+1))
                .append("\n    ")
                .append(logStackTrancInfo_real(trace, stackIndex));
        return methodLog.toString();
    }

}
