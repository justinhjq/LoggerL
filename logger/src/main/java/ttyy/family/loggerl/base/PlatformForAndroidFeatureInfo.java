package ttyy.family.loggerl.base;

import android.content.Context;

/**
 * Author: hujinqi
 * Date  : 2016-08-30
 * Description:
 */
public class PlatformForAndroidFeatureInfo {

    static Context mContext;

    public static void bindContext(Context context){
        mContext =  context.getApplicationContext();
    }

    public static Context bindedContext(){
        return mContext;
    }

}
