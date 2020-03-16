package me.soushin.tinmvc.module;

import android.Manifest;
import android.media.MediaRouter;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.blankj.ALog;
import me.soushin.tinmvc.R;
import me.soushin.tinmvc.base.BaseActivity;
import me.soushin.tinmvc.module.demo.ActiviteFragment;
import me.soushin.tinmvc.module.demo.HomeFragment;
import me.soushin.tinmvc.module.demo.MineFragment;
import me.soushin.tinmvc.utils.ActivityUtils;
import me.soushin.tinmvc.utils.AppUtils;
import me.soushin.tinmvc.utils.NetUtils;
import me.soushin.tinmvc.utils.StringUtils;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhouyou.http.subsciber.ProgressSubscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;


/**
 * 主页
 * 这里简单演示一下fragmentation 详细的使用请到(https://github.com/YoKeyword/Fragmentation)
 *
 * @author SouShin
 * @time 2018/8/28 17:51
 */
public class MainActivity extends BaseActivity implements OnTabSelectListener {

    @BindView(R.id.bottomBar)
    BottomBar bottomBar;
    List<SupportFragment> fragmentList;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        initPermission();
        fragmentList = new ArrayList<>();
        if (StringUtils.isNull(findFragment(HomeFragment.class))) {
            fragmentList.add(new HomeFragment());
            fragmentList.add(new ActiviteFragment());
            fragmentList.add(new MineFragment());
            loadMultipleRootFragment(R.id.fl_container, 0, fragmentList.get(0),
                    fragmentList.get(1), fragmentList.get(2));
        }else {
            fragmentList.add(findFragment(HomeFragment.class));
            fragmentList.add(findFragment(ActiviteFragment.class));
            fragmentList.add(findFragment(MineFragment.class));
        }
    }

    private void initPermission() {
        //获取权限
        new RxPermissions(getThis())
                .requestEachCombined(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new ProgressSubscriber<Permission>(getThis()) {
                    @Override
                    public void onNext(Permission permission) {
                        super.onNext(permission);
                        if (permission.granted){
                            ALog.e("权限已同意.....", permission.toString());
                        }else if (permission.shouldShowRequestPermissionRationale){
                            showToasty("权限已被拒绝,这将会导致部分功能不可用！");
                        }else {
                            ALog.e("权限被拒绝.....",  permission.toString());
                        }
                    }
                });
    }

    @Override
    protected void initListener() {
        bottomBar.setOnTabSelectListener(this);
    }

    @Override
    public void onTabSelected(int tabId) {
        switch (tabId){
            case R.id.tab_home:
//                showToasty("当前:"+bottomBar.getTabAtPosition(0).getTitle());
                showHideFragment(fragmentList.get(0));
                break;
            case R.id.tab_activity:
//                showToasty("当前:"+bottomBar.getTabAtPosition(1).getTitle());
                showHideFragment(fragmentList.get(1));
                break;
            case R.id.tab_mine:
//                showToasty("当前:"+bottomBar.getTabAtPosition(2).getTitle());
                showHideFragment(fragmentList.get(2));
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        inspectNet();
    }

    /**
     * 检测网络
     */
    private void inspectNet() {
        int netMobile = NetUtils.getNetWorkState(getThis());
        switch (netMobile) {
            case 1:
                ALog.e("网络状况:当前没有网络");
                showToasty("当前网络不可用,请检查网络是否连接");
                break;
            case 2:
                ALog.i("网络状况:连接移动数据");
                break;
            case 3:
                ALog.i("网络状况：连接wifi");
                break;
        }
    }

    @Override
    public void onBackPressedSupport() {
//        onBackPressed()已停用  请使用onBackPressedSupport代替
        if (AppUtils.doubleClickExit()) {
            ActivityUtils.AppExit(getThis());
        }
    }

}

