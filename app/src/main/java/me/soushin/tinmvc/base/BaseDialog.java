package me.soushin.tinmvc.base;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import me.soushin.tinmvc.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.soushin.tinmvc.utils.DialogUtils;

/**
 * Created by SouShin on 2018/8/111736.
 * 自定义dialog的基类
 */
public class BaseDialog extends Dialog{
    private Context mContext;
    private Unbinder unbinder;
    public BaseDialog(Context context, int layoutId) {
        this(context, layoutId, R.style.CustomDialog);
    }

    public BaseDialog(Context context, int layoutId, int styleId) {
        super(context, styleId);
        this.mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layoutId, null);
        this.setContentView(view);
        DialogUtils.addDialog(this);
        unbinder=ButterKnife.bind(this);
    }

    protected Context getContext_() {
        return this.mContext;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (unbinder!=null){
            unbinder.unbind();
        }
    }
}
