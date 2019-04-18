package me.soushin.tinmvc;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

/**
 * 应用初始化--故事的开始
 *
 * @auther SouShin
 * @time 2019/1/11 11:22
 */
public class App extends Application implements DefaultRefreshHeaderCreator,DefaultRefreshFooterCreator {
    private static App app;

    @Override
    public void onCreate() {
        super.onCreate();
        app=this;
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(this);
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(this);
    }

    public static App getApp() {
        return app;
    }

    @NonNull
    @Override
    public RefreshFooter createRefreshFooter(@NonNull Context context, @NonNull RefreshLayout layout) {
        //指定为经典Footer
        return new ClassicsFooter(context)
                .setAccentColor(context.getResources().getColor(R.color.colorAccent))
                .setDrawableSize(20);
    }

    @NonNull
    @Override
    public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout) {
        return new ClassicsHeader(context)
                .setAccentColor(context.getResources().getColor(R.color.colorAccent));
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

}
