package me.soushin.tinmvc.module.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.gyf.barlibrary.ImmersionBar;
import me.soushin.tinmvc.R;
import me.soushin.tinmvc.base.BaseFragment;

import me.soushin.tinmvc.base.BaseFragment;

/**
 * 动态
 *
 * @auther SouShin
 * @time 2019/1/18 14:42
 */
public class ActiviteFragment extends BaseFragment {

    @Override
    public int initLayout() {
        return R.layout.fragment_activite;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
    }

    @Override
    protected void initListener() {
    }

    @Override
    public void initImmersionBar() {
//  新手指引:  fragment里设置沉浸标题栏之前必须先在宿主Activity里调用ImmersionBar.with(this).init();
        ImmersionBar.with(this)//这里传入的this是fragment的上下文  不能是activity的
                .fitsSystemWindows(true)
                .statusBarDarkFont(true, 1f)
                .init();
    }

    @Override
    public boolean immersionBarEnabled() {
//      当为true的时候才可以执行initImmersionBar方法
        return true;
    }

}
