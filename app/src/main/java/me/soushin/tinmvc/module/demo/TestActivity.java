package me.soushin.tinmvc.module.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import me.soushin.tinmvc.R;
import me.soushin.tinmvc.base.BaseActivity;
import me.soushin.tinmvc.utils.AppUtils;
import me.soushin.tinmvc.utils.DataUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;
import me.soushin.tinmvc.base.BaseActivity;
import me.soushin.tinmvc.utils.AppUtils;
import me.soushin.tinmvc.utils.DataUtils;

/**
 * 测试Activity
 * BaseAdapter用法演示
 *
 * @auther SouShin
 * @time 2019/1/19 16:29
 */
public class TestActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener,OnRefreshLoadMoreListener {
    @BindView(R.id.rv_test)
    RecyclerView rvTest;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout srlRefresh;
    private TestAdapter testAdapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_test;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        testAdapter = new TestAdapter();
        rvTest.setLayoutManager(new LinearLayoutManager(getThis()));
        rvTest.setHasFixedSize(true);
        testAdapter.bindToRecyclerView(rvTest);
        srlRefresh.autoRefresh();
    }

    @Override
    protected void initListener() {
        srlRefresh.setOnRefreshLoadMoreListener(this);
        testAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        requestData();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        演示处理EditText显示隐藏输入法
        AppUtils.dispatchEditText(getThis(), ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        requestData();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        showToasty(testAdapter.getItem(position)+position);
    }

    //模拟请求接口
    private void requestData() {
        //访问接口之前
        if (srlRefresh.isRefreshing()) {
            testAdapter.setCurrent(0);
            srlRefresh.setEnableLoadMore(false);
        }
        //访问成功之后获取数据
        List<String> datalist = DataUtils.getActiveList();
        testAdapter.setCurrent(testAdapter.getCurrent() + 1)//设置当前页
                .setPageNo(5);//设置总页数
        if (srlRefresh.isRefreshing()) {
            testAdapter.setNewData(srlRefresh, datalist);
        } else {
            testAdapter.addData(srlRefresh, datalist);
        }
    }

}
