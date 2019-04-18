package me.soushin.tinmvc.utils;

import android.app.Dialog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import me.soushin.tinmvc.base.BaseDialog;

/**
 * Dialog工具类
 * @auther SouShin
 * @time 2019/1/16 09:13
 */
public class DialogUtils {
    private static List<BaseDialog> dialogList = new ArrayList<>();

    /**
     * 关闭弹出框
     * 最好在onDestroy里调用一下这个方法
     */
    public static void disDialog() {
        Iterator<BaseDialog> it = dialogList.iterator();
        while (it.hasNext()) {
            BaseDialog dialog = it.next();
            disDialog(dialog);
            it.remove();
        }
    }

    public static void addDialog(BaseDialog dialog) {
        dialogList.add(dialog);
    }

    public static void disDialog(Dialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

}
