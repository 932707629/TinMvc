package me.soushin.tinmvc.utils;

import android.app.Dialog;

/**
 * Dialog工具类
 * @auther SouShin
 * @time 2019/1/16 09:13
 */
public class DialogUtils {

    /**
     * 如果界面中有弹出dialog的需求
     * 尽量在onDestroy()中调用一下这个方法 关闭dialog
     * 否则不关闭dialog就执行onDestroy()会报错
     * @param dialog
     */
    public static void dissMiss(Dialog dialog){
        if (dialog!=null&&dialog.isShowing()){
            dialog.dismiss();
        }
    }

}
