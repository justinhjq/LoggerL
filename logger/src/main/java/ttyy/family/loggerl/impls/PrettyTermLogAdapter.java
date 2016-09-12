package ttyy.family.loggerl.impls;

import android.util.Log;

import ttyy.family.loggerl.base.LoggerAdapter;
import ttyy.family.loggerl.util.LUtil;

/**
 * Author: hujinqi
 * Date  : 2016-08-30
 * Description: 打印在Anroid logcat中的日志适配器
 */
public class PrettyTermLogAdapter implements LoggerAdapter {

    // 方框Log元素
    private static final char TOP_LEFT_CORNER = '╔';
    private static final char BOTTOM_LEFT_CORNER = '╚';
    private static final char MIDDLE_CORNER = '╟';
    private static final char HORIZONTAL_DOUBLE_LINE = '║';
    private static final String DOUBLE_DIVIDER = "════════════════════════════════════════════";
    private static final String SINGLE_DIVIDER = "────────────────────────────────────────────";
    private static final String TOP_BORDER = TOP_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
    private static final String BOTTOM_BORDER = BOTTOM_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
    private static final String MIDDLE_BORDER = MIDDLE_CORNER + SINGLE_DIVIDER + SINGLE_DIVIDER;

    private int MaxBytesLength = 4000;

    @Override
    public void log(int priority, String tag, String message) {
        String headLog = logThreadInfo();

        String traceLog = LUtil.logStackTraceInfo();

        byte[] bytes = message.getBytes();

        // 当前线程信息
        Log.println(priority, tag, TOP_BORDER);
        Log.println(priority, tag, HORIZONTAL_DOUBLE_LINE + " " + headLog);
        Log.println(priority, tag, MIDDLE_BORDER);

        // 堆栈信息
//        Log.println(priority, tag, HORIZONTAL_DOUBLE_LINE + " " + traceLog);
        logContent(priority, tag, traceLog);
        Log.println(priority, tag, MIDDLE_BORDER);

        // 内容
        for (int i = 0; i < bytes.length; i += MaxBytesLength) {
            int count = Math.min(bytes.length - i, MaxBytesLength);
            logContent(priority, tag, new String(bytes, i, count));
        }

        Log.println(priority, tag, BOTTOM_BORDER);


    }

    /**
     * 打印当前Thread 信息
     */
    String logThreadInfo() {
        StringBuilder threadLog = new StringBuilder();

        threadLog.append("Thread:")
                .append(Thread.currentThread().getName());
        return threadLog.toString();
    }

    // 打印内容实现
    void logContent(int priority, String tag, String chunk) {
        String[] lines = chunk.split(System.getProperty("line.separator", "\n"));
        for (String line : lines) {
            Log.println(priority, tag, HORIZONTAL_DOUBLE_LINE + " " + line);
        }
    }

}
