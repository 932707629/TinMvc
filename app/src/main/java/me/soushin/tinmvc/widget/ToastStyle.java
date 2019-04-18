package me.soushin.tinmvc.widget;

import android.view.Gravity;

import com.hjq.toast.IToastStyle;

/**
 * 自定义Toast样式
 *
 * @auther SouShin
 * @time 2018/12/27 09:45
 */
public class ToastStyle implements IToastStyle {

    @Override
    public int getGravity() {
        return Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM;
    }

    @Override
    public int getXOffset() {
        return 0;
    }

    @Override
    public int getYOffset() {
        return 240;
    }

    @Override
    public int getZ() {
        return 30;
    }

    @Override
    public int getCornerRadius() {
        return 5;
    }

    @Override
    public int getBackgroundColor() {
        return 0XEE575757;
    }

    @Override
    public int getTextColor() {
        return 0XFFFFFFFF;
    }

    @Override
    public float getTextSize() {
        return 14;
    }

    @Override
    public int getMaxLines() {
        return 3;
    }

    @Override
    public int getPaddingLeft() {
        return 20;
    }

    @Override
    public int getPaddingTop() {
        return 8;
    }

    @Override
    public int getPaddingRight() {
        return getPaddingLeft();
    }

    @Override
    public int getPaddingBottom() {
        return getPaddingTop();
    }
}
