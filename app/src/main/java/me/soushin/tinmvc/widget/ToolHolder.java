package me.soushin.tinmvc.widget;

import android.view.View;
import android.widget.TextView;

import me.soushin.tinmvc.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 标题栏
 * @auther SouShin
 * @time 2019/1/24 09:11
 */
public class ToolHolder {
    @BindView(R.id.view_back)
    public View viewBack;
    @BindView(R.id.tv_title)
    public TextView tvTitle;
    @BindView(R.id.toolbar)
    public View toolbar;
    public Unbinder unbinder;

    public ToolHolder(View view) {
        unbinder=ButterKnife.bind(this, view);
    }
}
