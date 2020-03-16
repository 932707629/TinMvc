package me.soushin.tinmvc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.blankj.ALog;

import me.soushin.tinmvc.base.BaseFragment;

/**
 * @auther SouShin
 * @time 2020/3/16 16:24
 */
public class FragmentLifecycleCallbacksImpl extends FragmentManager.FragmentLifecycleCallbacks {

    @Override
    public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
        super.onFragmentCreated(fm, f, savedInstanceState);
        ALog.e("onFragmentCreated",f.getClass().getSimpleName());
        if (f instanceof BaseFragment &&((BaseFragment) f).useEventBus()){
            //eventbus注册代码

        }
    }

    @Override
    public void onFragmentDetached(FragmentManager fm, Fragment f) {
        super.onFragmentDetached(fm, f);
        if (f instanceof BaseFragment &&((BaseFragment) f).useEventBus()){
            //eventbus解绑代码

        }
    }
}
