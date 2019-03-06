package me.soushin.tinmvc.base;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import me.soushin.tinmvc.R;
import me.soushin.tinmvc.utils.StringUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.Collection;
import java.util.List;

/**
 * 基于SmartRefresh和BaseQuickAdapter封装的BaseAdapter
 * 使用之前先设当前页 current  和总页 pageNo
 * 设置adapter请用bindToRecyclerView();这种方式
 *
 * @author SouShin
 * @time 2018/11/13 11:34
 */
public class BaseAdapter<T, K extends BaseViewHolder> extends BaseQuickAdapter<T, K> {
    private int pageNo;
    private int current;

    public BaseAdapter(int layoutResId) {
        super(layoutResId);
    }

    public BaseAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    public BaseAdapter(@Nullable List<T> data) {
        super(data);
    }

    @Override
    protected void convert(K helper, T item) {}

    /**
     * 刷新数据
     * 使用默认空布局
     *
     * @param refresh
     * @param data
     */
    public void setNewData(SmartRefreshLayout refresh, @Nullable List<T> data) {
        setNewData(refresh, data, null);
    }

    /**
     * 刷新数据
     * 使用自定义空布局
     * 使用之前先设当前页 current  和总页 pageNo
     *
     * @param refresh
     * @param data
     */
    public void setNewData(SmartRefreshLayout refresh, @Nullable List<T> data,@Nullable Integer emptyLayout) {
        if (refresh==null){
            return;
        }
        if (getRecyclerView()==null){
            throw new NullPointerException("新手指引:设置adapter请用bindToRecyclerView();这种方式");
        }
        // TODO: 2018/11/13 设置空布局
        emptyLayout = emptyLayout == null ? R.layout.layout_empty : emptyLayout;
        refresh.finishRefresh();
        setNewData(data);
        if (StringUtils.isNull(data)) {
            refresh.setEnableLoadMore(false);
            setEmptyView(emptyLayout, (ViewGroup) getRecyclerView().getParent());
        }else {
            refresh.setEnableLoadMore(true);
        }
        refresh.setNoMoreData(!hasMore());
    }

    /**
     * 加载数据
     * 使用之前先设当前页 current  和总页 pageNo
     *
     * @param refresh
     * @param newData
     */
    public void addData(SmartRefreshLayout refresh, @NonNull Collection<? extends T> newData) {
        if (refresh==null){
            return;
        }
        addData(newData);
        if (hasMore()) {
            refresh.finishLoadMore();
        } else {
            refresh.finishLoadMoreWithNoMoreData();
        }
    }

    /**
     * 加载失败
     * @param refresh
     */
    public void loadFail(SmartRefreshLayout refresh) {
        if (refresh==null){
            return;
        }
        if (refresh.isRefreshing()) {
            refresh.finishRefresh(0);
        } else {
            refresh.finishLoadMore(0);
        }
        refresh.setEnableLoadMore(true);
    }

    /**
     * true 可以加载更多  false  没有更多了
     *
     * 先setPageNo&setCurrent再调用setNewData/addData
     * @return
     */
    public boolean hasMore() {
        return current < pageNo;
    }

    public BaseAdapter setPageNo(int pageNo) {
        this.pageNo = pageNo;
        return this;
    }

    public int getCurrent() {
        return current;
    }

    public BaseAdapter setCurrent(int current) {
        this.current = current;
        return this;
    }
}
