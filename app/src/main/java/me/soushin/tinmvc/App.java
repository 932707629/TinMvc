package me.soushin.tinmvc;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;
import android.view.View;

import com.blankj.ALog;
import com.hjq.toast.ToastUtils;
import me.soushin.tinmvc.network.TokenInterceptor;
import me.soushin.tinmvc.utils.SharedUtils;
import me.soushin.tinmvc.utils.StringUtils;
import me.soushin.tinmvc.widegt.ToastStyle;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.cache.converter.SerializableDiskConverter;
import com.zhouyou.http.cookie.CookieManger;

import java.util.ArrayList;

import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.internal.CustomAdapt;

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
        initApp();
    }

    private void initApp() {
        app=this;
        initALog();
        initAutoSize();
        ToastUtils.init(this, new ToastStyle());
//        注册ActivityLifeCycleCallBackIml必须要在初始化alog之后 因为里面有用到alog
        registerActivityLifecycleCallbacks(new ActivityLifeCycleCallBackIml());
        SharedUtils.initShared(app);
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(this);
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(this);
//        initEasyHttp();  网络框架初始化
    }

    // init it in ur application
    public void initALog() {
        ALog.init(this)
                .setLogSwitch(BuildConfig.DEBUG)// 设置 log 总开关，包括输出到控制台和文件，默认开
                .setConsoleSwitch(BuildConfig.DEBUG)// 设置是否输出到控制台开关，默认开
                .setGlobalTag(null)// 设置 log 全局标签，默认为空
                // 当全局标签不为空时，我们输出的 log 全部为该 tag，
                // 为空时，如果传入的 tag 为空那就显示类名，否则显示 tag
                .setLogHeadSwitch(true)// 设置 log 头信息开关，默认为开
                .setLog2FileSwitch(false)// 打印 log 时是否存到文件的开关，默认关
                .setDir("")// 当自定义路径为空时，写入应用的 /cache/log/ 目录中
                .setFilePrefix("")// 当文件前缀为空时，默认为 "alog"，即写入文件为 "alog-MM-dd.txt"
                .setBorderSwitch(false)// 输出日志是否带边框开关，默认开
                .setSingleTagSwitch(true)// 一条日志仅输出一条，默认开，为美化 AS 3.1 的 Logcat
                .setConsoleFilter(ALog.V)// log 的控制台过滤器，和 logcat 过滤器同理，默认 Verbose
                .setFileFilter(ALog.V)// log 文件过滤器，和 logcat 过滤器同理，默认 Verbose
                .setStackDeep(1)// log 栈深度，默认为 1
                .setStackOffset(0)// 设置栈偏移，比如二次封装的话就需要设置，默认为 0
                .setSaveDays(1)// 设置日志可保留天数，默认为 -1 表示无限时长
                // 新增 ArrayList 格式化器，默认已支持 Array, Throwable, Bundle, Intent 的格式化输出
                .addFormatter(new ALog.IFormatter<ArrayList>() {
                    @Override
                    public String format(ArrayList list) {
                        return "ALog Formatter ArrayList { " + list.toString() + " }";
                    }
                });
    }

    /**
     * ================================================
     * https://github.com/JessYanCoding/AndroidAutoSize
     * AndroidAutoSize框架核心原理来自于 <a href="https://mp.weixin.qq.com/s/d9QCoBP6kV9VSWvVldVVwA">今日头条官方适配方案</a>
     * 此方案不光可以适配 {@link Activity}, 这个 {@link Activity} 下的所有 {@link Fragment}、{@link Dialog}、{@link View} 都会自动适配
     * <p>
     * Activity 是以屏幕宽度为基准进行适配的, 并且使用的是在 AndroidManifest 中填写的全局设计图尺寸 360 * 640
     * 不懂什么叫基准的话, 请看 {@link AutoSizeConfig#isBaseOnWidth}) 的注释, AndroidAutoSize 默认全局以屏幕宽度为基准进行适配
     * 如果想更改为全局以屏幕高度为基准进行适配, 请在 {@link App} 中按注释中更改, 为什么强调全局？
     * 因为 AndroidAutoSize 允许每个 {@link Activity} 可以自定义适配参数, 自定义适配参数通过实现 {@link CustomAdapt}
     * 如果不自定义适配参数就会使用全局的适配参数, 全局适配参数在 {@link App} 中按注释设置
     * <p>
     * ================================================
     */
    private void initAutoSize() {
        //当 App 中出现多进程, 并且您需要适配所有的进程, 就需要在 App 初始化时调用 initCompatMultiProcess()
//        AutoSize.initCompatMultiProcess(this);

        AutoSizeConfig.getInstance()
                //是否让框架支持自定义 Fragment 的适配参数, 由于这个需求是比较少见的, 所以须要使用者手动开启
                //如果没有这个需求建议不开启
//                .setCustomFragment(false)
                //是否屏蔽系统字体大小对 AndroidAutoSize 的影响, 如果为 true, App 内的字体的大小将不会跟随系统设置中字体大小的改变
                //如果为 false, 则会跟随系统设置中字体大小的改变, 默认为 false
                .setExcludeFontScale(true)
                //屏幕适配监听器
//                .setOnAdaptListener(new onAdaptListener() {
//                    @Override
//                    public void onAdaptBefore(Object target, Activity activity) {
//                        //使用以下代码, 可支持 Android 的分屏或缩放模式, 但前提是在分屏或缩放模式下当用户改变您 App 的窗口大小时
//                        //系统会重绘当前的页面, 经测试在某些机型, 某些情况下系统不会重绘当前页面, ScreenUtils.getScreenSize(activity) 的参数一定要不要传 Application!!!
//                        AutoSizeConfig.getInstance().setScreenWidth(ScreenUtils.getScreenSize(activity)[0]);
//                        AutoSizeConfig.getInstance().setScreenHeight(ScreenUtils.getScreenSize(activity)[1]);
//                        LogUtils.d(String.format(Locale.ENGLISH, "%s onAdaptBefore!", target.getClass().getName()));
//                    }
//
//                    @Override
//                    public void onAdaptAfter(Object target, Activity activity) {
//                        LogUtils.d(String.format(Locale.ENGLISH, "%s onAdaptAfter!", target.getClass().getName()));
//                    }
//                })
        //是否打印 AutoSize 的内部日志, 默认为 true, 如果您不想 AutoSize 打印日志, 则请设置为 false
//                .setLog(false)
        //是否使用设备的实际尺寸做适配, 默认为 false, 如果设置为 false, 在以屏幕高度为基准进行适配时
        //AutoSize 会将屏幕总高度减去状态栏高度来做适配
        //设置为 true 则使用设备的实际屏幕高度, 不会减去状态栏高度
//                .setUseDeviceSize(true)
        //是否全局按照宽度进行等比例适配, 默认为 true, 如果设置为 false, AutoSize 会全局按照高度进行适配
                .setBaseOnWidth(true)
        //设置屏幕适配逻辑策略类, 一般不用设置, 使用框架默认的就好
//                .setAutoAdaptStrategy(new AutoAdaptStrategy())
        ;

//        给外部的三方库 {@link Activity} 自定义适配参数, 因为三方库的 {@link Activity} 并不能通过实现
//        {@link CustomAdapt} 接口的方式来提供自定义适配参数 (因为远程依赖改不了源码)
//         所以使用 {@link ExternalAdaptManager } 来替代实现接口的方式, 来提供自定义适配参数
//        {@link ExternalAdaptManager} 是一个管理外部三方库的适配信息和状态的管理类, 详细介绍请看 {@link ExternalAdaptManager} 的类注释

//        AutoSizeConfig.getInstance().getExternalAdaptManager()

                //加入的 Activity 将会放弃屏幕适配, 一般用于三方库的 Activity, 详情请看方法注释
                //如果不想放弃三方库页面的适配, 请用 addExternalAdaptInfoOfActivity 方法, 建议对三方库页面进行适配, 让自己的 App 更完美一点
//                .addCancelAdaptOfActivity(DefaultErrorActivity.class)

                //为指定的 Activity 提供自定义适配参数, AndroidAutoSize 将会按照提供的适配参数进行适配, 详情请看方法注释
                //一般用于三方库的 Activity, 因为三方库的设计图尺寸可能和项目自身的设计图尺寸不一致, 所以要想完美适配三方库的页面
                //就需要提供三方库的设计图尺寸, 以及适配的方向 (以宽为基准还是高为基准?)
                //三方库页面的设计图尺寸可能无法获知, 所以如果想让三方库的适配效果达到最好, 只有靠不断的尝试
                //由于 AndroidAutoSize 可以让布局在所有设备上都等比例缩放, 所以只要您在一个设备上测试出了一个最完美的设计图尺寸
                //那这个三方库页面在其他设备上也会呈现出同样的适配效果, 等比例缩放, 所以也就完成了三方库页面的屏幕适配
                //即使在不改三方库源码的情况下也可以完美适配三方库的页面, 这就是 AndroidAutoSize 的优势
                //但前提是三方库页面的布局使用的是 dp 和 sp, 如果布局全部使用的 px, 那 AndroidAutoSize 也将无能为力
                //经过测试 DefaultErrorActivity(第三方库的Activity) 的设计图宽度在 380dp - 400dp 显示效果都是比较舒服的
//                .addExternalAdaptInfoOfActivity(DefaultErrorActivity.class, new ExternalAdaptInfo(true, 400));
    }

    /**
     * 网络框架初始化
     * 这里加了判空,初始化之前先设置baseurl
     * 没有设置会报异常NullPointerException
     */
    private void initEasyHttp() {
        if (StringUtils.isNull(Configure.BASE_URL)) {
            throw new NullPointerException("新手指引:请在Configure.class中设置BASE_URL");
        }
        EasyHttp.init(this);
        EasyHttp.getInstance()
                .debug("NetWork--INFO",BuildConfig.DEBUG)
                .setReadTimeOut(20 * 1000)
                .setWriteTimeOut(20 * 1000)
                .setConnectTimeout(20 * 1000)
                .setRetryCount(3)//默认网络不好自动重试3次
                .setBaseUrl(Configure.BASE_URL)
                .setCacheDiskConverter(new SerializableDiskConverter())//默认缓存使用序列化转化
                .setCacheMaxSize(50 * 1024 * 1024)//设置缓存大小为50M
                .setCacheVersion(1)//缓存版本为1
                .addInterceptor(new TokenInterceptor())
                .setCookieStore(new CookieManger(this))
                .setCertificates();//信任所有证书
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
