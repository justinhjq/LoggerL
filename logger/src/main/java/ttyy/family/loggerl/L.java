package ttyy.family.loggerl;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ttyy.family.loggerl.base.LoggerAdapter;
import ttyy.family.loggerl.base.PlatformForAndroidFeatureInfo;
import ttyy.family.loggerl.util.LUtil;

/**
 * Author: hujinqi
 * Date  : 2016-08-30
 * Description:
 */
public class L {

    static List<LoggerAdapter> loggers = new ArrayList<LoggerAdapter>(){
        {
            add(LoggerAdapter.Default);
            add(LoggerAdapter.File);
        }
    };

    static ThreadLocal<List<LoggerAdapter>> localLoggers = new ThreadLocal<List<LoggerAdapter>>() {
        @Override
        protected List<LoggerAdapter> initialValue() {
            return loggers;
        }
    };


    /**
     * 初始化文件记录的上下文
     * @param context
     */
    public static void initStoredFileContext(Context context){
        PlatformForAndroidFeatureInfo.bindContext(context);
    }

    /**
     * Log.i
     *
     * @param tag
     * @param message
     */
    public static void i(String tag, String message) {
        for(LoggerAdapter logger : localLoggers.get()){
            logger.log(Log.INFO, tag, message);
        }
    }

    /**
     * Log.d
     *
     * @param tag
     * @param message
     */
    public static void d(String tag, String message) {
        for(LoggerAdapter logger : localLoggers.get()){
            logger.log(Log.DEBUG, tag, message);
        }
    }

    /**
     * Log.w
     *
     * @param tag
     * @param throwable
     */
    public static void w(String tag, Throwable throwable) {
        String str = LUtil.format(throwable);
        w(tag, str);
    }

    /**
     * Log.w
     *
     * @param tag
     * @param message
     */
    public static void w(String tag, String message) {
        for(LoggerAdapter logger : localLoggers.get()){
            logger.log(Log.WARN, tag, message);
        }
    }

    /**
     * Log.e
     *
     * @param tag
     * @param message
     */
    public static void e(String tag, String message) {
        for(LoggerAdapter logger : localLoggers.get()){
            logger.log(Log.ERROR, tag, message);
        }
    }

    /**
     * 打印json
     *
     * @param tag
     * @param json
     */
    public static void json(String tag, String json) {
        try {
            if (json.trim().startsWith("{")) {
                JSONObject object = new JSONObject(json);
                d(tag, object.toString(2));
            } else if (json.trim().startsWith("[")) {
                JSONArray array = new JSONArray(json);
                d(tag, array.toString(2));
            } else {
                d(tag, json);
            }
        } catch (JSONException e) {
            e(tag, "Invalidate Json!");
        }
    }

}
