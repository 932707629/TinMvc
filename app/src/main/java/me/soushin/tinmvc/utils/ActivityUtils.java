package me.soushin.tinmvc.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.Stack;

/**
 * 封装Activity相关工具类
 *
 * @author SouShin
 * @time 2018/10/29 14:45
 */
public class ActivityUtils {

    private static Stack<Activity> activityStack;

    /**
     * 添加Activity 到栈
     *
     * @param activity
     */
    public static void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前的Activity（堆栈中最后一个压入的)
     */
    public static Activity currentActivity() {
        return activityStack.lastElement();
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public static void finishActivity() {
        Activity activity = activityStack.lastElement();
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 移除指定的Activity
     *
     * @param activity
     */
    public static void removeActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
        }
    }

    /**
     * 结束指定的Activity
     *
     * @param activity
     */
    public static void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public static void finishActivity(Class<?> cls) {
        for (int i = 0; i < activityStack.size(); i++) {
            if (activityStack.get(i).getClass().equals(cls)) {
                finishActivity(activityStack.get(i));
            }
        }
    }

    /**
     * 结束所有的Activity
     */
    public static void finishAllActivity() {
        int size = activityStack.size();
        for (int i = 0; i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    public static Stack<Activity> getActivityStack() {
        return activityStack;
    }

    /**
     * 判断是否存在指定Activity
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isExistActivity(Class clazz) {
        return activityStack.contains(clazz);
    }

    /**
     * 要求最低API为11
     * Activity 跳转
     * 跳转后Finish之前所有的Activity
     *
     * @param activity 上下文
     * @param goal 目标
     */
    public static void goAndFinishAll(Activity activity, Class<?> goal, Bundle bundle) {
        Intent intent = new Intent(activity, goal);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
        activity.finish();
    }

    /**
     * 要求最低API为11
     * Activity 跳转
     * 跳转后Finish之前所有的Activity
     *
     * @param activity 上下文
     * @param goal 目标
     */
    public static void goAndFinishAll(Activity activity, Class<?> goal) {
        Intent intent = new Intent(activity, goal);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
        activity.finish();
    }

    /**
     * Activity 跳转
     *
     * @param activity 上下文
     * @param goal 目标
     */
    public static void goAndFinish(Activity activity, Class<?> goal, Bundle bundle) {
        Intent intent = new Intent(activity, goal);
        intent.putExtras(bundle);
        activity.startActivity(intent);
        activity.finish();
    }

    /**
     * Activity 跳转
     *
     * @param activity 上下文
     * @param goal 目标
     */
    public static void goAndFinish(Activity activity, Class<?> goal) {
        activity.startActivity(new Intent(activity, goal));
        activity.finish();
    }

    /**
     * Activity 跳转
     *
     * @param activity 上下文
     * @param goal 目标
     */
    public static void goTo(Activity activity, Class<?> goal) {
        Intent intent = new Intent(activity, goal);
        activity.startActivity(intent);
    }

    /**
     * Activity 跳转
     *
     * @param activity 上下文
     * @param goal 目标
     */
    public static void goTo(Activity activity, Class<?> goal, Bundle bundle) {
        Intent intent = new Intent(activity, goal);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    /**
     * activity跳转
     * @param activity 上下文
     * @param goal 目标
     * @param requestCode 请求码
     */
    public static void goForResult(Activity activity, Class<?> goal, int requestCode) {
        activity.startActivityForResult(new Intent(activity, goal), requestCode);
    }

    /**
     * activity跳转
     * @param activity  上下文
     * @param goal 目标
     * @param bundle 参数
     * @param requestCode 请求码
     */
    public static void goForResult(Activity activity, Class<?> goal, Bundle bundle, int requestCode) {
        Intent intent = new Intent(activity, goal);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 退出app
     *
     * @param context
     */
    public static void AppExit(Context context) {
        try {
            finishAllActivity();
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityManager.killBackgroundProcesses(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {}
    }

}
