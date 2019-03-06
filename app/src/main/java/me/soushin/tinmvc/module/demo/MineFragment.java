package me.soushin.tinmvc.module.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import me.soushin.tinmvc.R;
import me.soushin.tinmvc.base.BaseFragment;

import butterknife.BindView;
import me.soushin.tinmvc.base.BaseFragment;

/**
 * 我的
 *
 * @auther SouShin
 * @time 2019/1/18 14:44
 */
public class MineFragment extends BaseFragment {
    @BindView(R.id.view_back)
    TextView tvBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;

    @Override
    public int initLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        tvTitle.setText("我的");
        tvBack.setVisibility(View.GONE);
        toolbar.setBackgroundResource(R.drawable.ic_launcher_background);
    }

    @Override
    protected void initListener() {}

    @Override
    public void initImmersionBar() {
//  新手指引:  fragment里设置沉浸标题栏之前必须先在宿主Activity里调用ImmersionBar.with(this).init();
        ImmersionBar.with(this)//这里传入的this是fragment的上下文  不能是activity的
                .titleBar(R.id.toolbar)
                .init();
    }

    @Override
    public boolean immersionBarEnabled() {
//      当为true的时候才可以执行initImmersionBar方法
        return true;
    }

}
