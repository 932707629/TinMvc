package me.soushin.tinmvc.module.demo;

import com.chad.library.adapter.base.BaseViewHolder;
import me.soushin.tinmvc.R;
import me.soushin.tinmvc.base.BaseAdapter;

import me.soushin.tinmvc.base.BaseAdapter;


/**
 * baseAdapter的简单使用
 * @auther SouShin
 * @time 2019/1/19 18:14
 */
public class TestAdapter extends BaseAdapter<String, BaseViewHolder> {
    public TestAdapter() {
        super(R.layout.layout_tool_bar);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_title,item)
        .setBackgroundColor(R.id.toolbar,mContext.getResources().getColor(R.color.white))
        .setTextColor(R.id.tv_title,mContext.getResources().getColor(R.color.black));
    }
}
