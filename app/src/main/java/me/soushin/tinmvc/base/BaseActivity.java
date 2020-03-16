package me.soushin.tinmvc.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.hjq.toast.ToastUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by puyanming on 16/5/9.
 * 这是Activity的基类
 * 继承SupportActivity 解决fragment重叠问题
 * 为"单Activity ＋ 多Fragment","多模块Activity + 多Fragment"架构而生，简化开发，轻松解决动画、嵌套、事务相关等问题
 * (https://github.com/YoKeyword/Fragmentation)
 */
public abstract class BaseActivity extends SupportActivity {
    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        unbinder = ButterKnife.bind(this);
        initView(savedInstanceState);
        initListener();
    }

    //这是加载布局的抽象方法
    protected abstract int getLayout();

    //这是加载View的方法
    protected abstract void initView(@Nullable Bundle savedInstanceState);

    //这是初始化监听的方法
    protected abstract void initListener();

    /**
     * 获取上下文
     *
     * @return
     */
    protected AppCompatActivity getThis() {
        return this;
    }

    public boolean useEventBus(){
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
        startActivity(new Intent(getThis(), clazz));
    }

    /**
     * 页面跳转
     *
     * @param clazz
     * @param intent
     */
    protected void startActivity(Class clazz, Intent intent) {
        intent.setClass(getThis(), clazz);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
