package ttyy.family.loggerl.impls;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ttyy.family.loggerl.base.LoggerAdapter;
import ttyy.family.loggerl.base.PlatformForAndroidFeatureInfo;
import ttyy.family.loggerl.util.LUtil;

/**
 * Author: hujinqi
 * Date  : 2016-08-30
 * Description:
 */
public class WriteExternalLogAdapter implements LoggerAdapter{

    String defaultDirName = "log";
    String recordPath;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm::ss");

    @Override
    public void log(int priority, String tag, String message) {
        tryInit();

        // 路径为空 无法记录
        if(TextUtils.isEmpty(recordPath)){
            return;
        }

        File tagFile = genTagFile(tag);
        if(tagFile == null){
            // 没权限 导致记录文件失败
            return;
        }

        FileWriter fw = null;
        try {
            fw = new FileWriter(tagFile , true);
            fw.append(wrapMsg(message));
            fw.append("\n");
            fw.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(fw != null){
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 基本配置初始化
     */
    void tryInit(){
        Context context = PlatformForAndroidFeatureInfo.bindedContext();
        // 没有上下文 无法进行文件写入
        if(context == null)
            return;

        // 初始化日志文件存储路径
        if(TextUtils.isEmpty(recordPath)){
            if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                // sdcard 存在
                if(context.getExternalFilesDir(defaultDirName)!=null)
                    recordPath = context.getExternalFilesDir(defaultDirName).getPath();
                else
                    recordPath = null;
            }else{
                // sdcard 不存在
                recordPath = null;
            }
        }
    }

    /**
     * tagFile to record the msg
     */
    private File genTagFile(String tag){
        StringBuilder pathBuilder = new StringBuilder(recordPath);
        pathBuilder.append("/")
                .append(tag)
                .append("/")
                .append(tagName())
                .append(".txt");
        File file = new File(pathBuilder.toString());
        File parentFile = file.getParentFile();

        // 创建父目录
        parentFile.mkdirs();

        if(file.exists()){
            return file;
        }else{
            try {
                file.createNewFile();
                return file;
            } catch (IOException e) {
                return null;
            }
        }
    }

    private String tagName(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int date = calendar.get(Calendar.DATE);
        StringBuilder sb = new StringBuilder();
        sb.append(year)
                .append("_")
                .append(month)
                .append("_")
                .append(date);

        return sb.toString();
    }

    /**
     * wrap the msg
     */
    private String wrapMsg(String msg){

        StringBuilder msgNew = new StringBuilder();
        String stackTraceInfo = LUtil.logStackTraceInfo(6);
        msgNew.append(" ")
                .append(currentTime())
                .append( ":  ")
                .append(stackTraceInfo)
                .append(">> ")
                .append(msg);

        return msgNew.toString();
    }

    /**
     * get current time
     */
    private String currentTime(){

        Date date = new Date();
        String dateStr = sdf.format(date);

        return dateStr;
    }

}
