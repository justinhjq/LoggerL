package ttyy.family.loggerl.base;

import ttyy.family.loggerl.impls.WriteExternalLogAdapter;
import ttyy.family.loggerl.impls.PrettyTermLogAdapter;

/**
 * Author: hujinqi
 * Date  : 2016-08-30
 * Description: 适配器
 */
public interface LoggerAdapter {

    void log(int priority, String tag, String message);

    LoggerAdapter Default = new PrettyTermLogAdapter();
    LoggerAdapter File = new WriteExternalLogAdapter();

}
