package me.soushin.tinmvc;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.blankj.ALog;
import com.gyf.barlibrary.ImmersionBar;

import java.util.HashMap;

import me.soushin.tinmvc.base.BaseActivity;
import me.soushin.tinmvc.module.MainActivity;
import me.soushin.tinmvc.module.demo.TestActivity;
import me.soushin.tinmvc.network.HttpClient;
import me.soushin.tinmvc.utils.ActivityUtils;
import me.soushin.tinmvc.utils.StringUtils;
import me.soushin.tinmvc.utils.TitleBarUtils;

/**
 * activity生命周期管理类
 * 你想象力有多丰富,这里就有多强大,
 * 以前放到BaseActivity的操作都可以放到这里
 * 使用:registerActivityLifecycleCallbacks(new ActivityLifeCycleCallBackIml());
 *
 * @author SouShin
 * @time 2018/12/10 15:38
 */
public class ActivityLifeCycleCallBackIml implements Application.ActivityLifecycleCallbacks {
    private TitleBarUtils titleBar;
    private HashMap<String, FragmentLifecycleCallbacksImpl> lifecycleCallbacksHashMap=new HashMap<>();

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        ALog.i("onActivityCreated",activity.getClass().getSimpleName());
        ActivityUtils.addActivity(activity);
        if (activity instanceof FragmentActivity){
            FragmentLifecycleCallbacksImpl lifecycleCallbacks=new FragmentLifecycleCallbacksImpl();
            lifecycleCallbacksHashMap.put(activity.getClass().getSimpleName(),lifecycleCallbacks);
            ((FragmentActivity) activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(lifecycleCallbacks,true);
        }
        if (activity instanceof BaseActivity &&((BaseActivity) activity).useEventBus()){
            //eventbus注册代码
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {
        ALog.i("activity生命周期管理类", "onActivityStarted");
//        这里根据不同的activity显示不同的toolBar
//        onActivityCreated()方法回调在activity的setContentView()之前,所以要在onActivityStarted()设置toolbar
        titleBar=new TitleBarUtils();
        if (activity instanceof MainActivity) {
            ImmersionBar.with(activity).init();
        }else if (activity instanceof TestActivity){
            titleBar.setToolBar(activity,true,"测试");
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        ALog.i("activity生命周期管理类", "onActivityResumed");
    }

    @Override
    public void onActivityPaused(Activity activity) {
        ALog.i("activity生命周期管理类", "onActivityPaused");
    }

    @Override
    public void onActivityStopped(Activity activity) {
        ALog.i("activity生命周期管理类", "onActivityStopped");
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        ALog.i("activity生命周期管理类", "onActivitySaveInstanceState");
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        ALog.i("activity生命周期管理类", "onActivityDestroyed");
        ImmersionBar.with(activity).destroy(); //必须调用该方法，防止内存泄漏
        ActivityUtils.removeActivity(activity);
        HttpClient.disposeRequest(activity.getLocalClassName());
        if (activity instanceof FragmentActivity){
            FragmentLifecycleCallbacksImpl lifecycleCallbacks=lifecycleCallbacksHashMap.get(activity.getClass().getSimpleName());
            ((FragmentActivity) activity).getSupportFragmentManager().unregisterFragmentLifecycleCallbacks(lifecycleCallbacks);
            lifecycleCallbacksHashMap.remove(activity.getClass().getSimpleName());
        }
        if (activity instanceof BaseActivity&&((BaseActivity) activity).useEventBus()){
            //eventbus解绑代码
        }
        if (!StringUtils.isNull(titleBar)){
            titleBar.Unbind();
            titleBar=null;
        }
    }

}
