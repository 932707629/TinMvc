package me.soushin.tinmvc.base;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyf.barlibrary.SimpleImmersionOwner;
import com.gyf.barlibrary.SimpleImmersionProxy;
import com.hjq.toast.ToastUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by puyanming on 16/5/9.
 * 这是Fragment的基类
 * 继承SupportFragment 解决fragment重叠问题
 * (https://github.com/YoKeyword/Fragmentation)
 * 提供onSupportVisible()、懒加载onLazyInitView()等生命周期方法，简化嵌套Fragment的开发过程
 */
public abstract class BaseFragment extends SupportFragment implements SimpleImmersionOwner {
    private Unbinder unbinder;
    //ImmersionBar代理类
    private SimpleImmersionProxy mSimpleImmersionProxy = new SimpleImmersionProxy(this);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(initLayout(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        initView(savedInstanceState);
        initListener();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mSimpleImmersionProxy.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSimpleImmersionProxy.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        mSimpleImmersionProxy.onHiddenChanged(hidden);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mSimpleImmersionProxy.onConfigurationChanged(newConfig);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSimpleImmersionProxy.onDestroy();
        unbinder.unbind();
    }

    public abstract int initLayout();

    //这是加载View的方法
    protected abstract void initView(@Nullable Bundle savedInstanceState);

    //这是初始化监听的方法
    protected abstract void initListener();

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
//        只有第一次可见时调用
//        ALog.i("懒加载",getClass().getSimpleName());
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
//        ALog.i("fragment隐藏",getClass().getSimpleName());
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
//        ALog.i("fragment可见",getClass().getSimpleName());
    }

    /**
     * 获取上下文
     *
     * @return
     */
    protected FragmentActivity getActivity_() {
        return _mActivity;
    }

    @Override
    public void initImmersionBar() {}

    @Override
    public boolean immersionBarEnabled() {
//        当为true的时候才可以执行initImmersionBar方法
        return false;
    }
    /**
     * 显示toast
     *
     * @param msg
     */
    protected void showToasty(String msg) {
        ToastUtils.show(msg);
    }

    /**
     * 页面跳转
     *
     * @param clazz
     */
    protected void startActivity(Class clazz) {
        startActivity(new Intent(getActivity(), clazz));
    }

    /**
     * 页面跳转
     *
     * @param clazz
     * @param intent
     */
    protected void startActivity(Class clazz, Intent intent) {
        intent.setClass(getActivity(), clazz);
        startActivity(intent);
    }

}