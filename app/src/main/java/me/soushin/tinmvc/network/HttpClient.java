package me.soushin.tinmvc.network;

import com.blankj.ALog;
import me.soushin.tinmvc.model.SubcribeModel;
import me.soushin.tinmvc.utils.ActivityUtils;
import me.soushin.tinmvc.utils.AppUtils;
import me.soushin.tinmvc.utils.StringUtils;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.body.ProgressResponseCallBack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * 网络交互
 * RxEasyHttp 详情链接(https://github.com/zhou-you/RxEasyHttp)
 * @warning 由于Retrofit的限制,传递的参数不能为空!
 * @author SouShin
 * @time 2018/11/16 17:31
 */
public class HttpClient {
    public static List<SubcribeModel> disposList = new ArrayList<>();
    /**
     * file上传结果回调
     */
    private static ProgressResponseCallBack fileProgressCallBack = new ProgressResponseCallBack() {
        @Override
        public void onResponseProgress(long bytesWritten, long contentLength, boolean done) {
            ALog.e("file上传回调", bytesWritten, contentLength, done);
        }
    };

    /**
     * 使用示例1 带有加载框的请求
     * ResultModel<Object>:ResultModel是与后台约定的数据返回类型  Object是需要在回调中返回的对象
     * ProgressSubscriber<Object>要求传入一个dialog对象
     * 注意这个dialog必须实现IProgressDialog接口
     * 错误回调 super.onError(e);必须写 因为里面加了订阅关系的处理
     *
     * @param phone 手机号
     * @param path 文件路径
     * @param progressCallBack  带有加载框的回调

    public static void getPhoneCode(String phone,String path, ProgressSubscriber<Object> progressCallBack) {
        EasyHttp.post("get_code")
                .params("phone", phone)
                .params("file",new File(path),fileProgressCallBack)
                .execute(new CallClazzProxy<ResultModel<Object>, Object>(Object.class) {
                }).subscribe(progressCallBack);
    }*/

    /**
     * 使用示例2 普通网络请求
     * ResultModel<Object>:ResultModel是与后台约定的数据返回类型  Object是需要在回调中返回的对象
     * 返回Disposable对象，方便取消网络请求 记得调用addDisposable(disposable) 把订阅添加到集合中
     * ActivityLifeCycleCallBackIml中已经做了解除订阅的处理
     *
     * @param phone 手机号
     * @param path 文件路径
     * @param simpleCallBack  没有加载框的回调

    public static void getPhoneCode(String phone, String path, SimpleCallBack<Object> simpleCallBack) {
        Disposable disposable=EasyHttp.post("get_code")
                .params("phone", phone)
                .params("file",new File(path),fileProgressCallBack)
                .execute(new CallBackProxy<ResultModel<Object>, Object>(simpleCallBack) {
                });
        addDisposable(disposable);
    }*/

    /**
     * 使用示例3 请求返回Observable
     * ResultModel<Object>:ResultModel是与后台约定的数据返回类型  Object是需要在回调中返回的对象
     * 返回Observable<Object>对象，方便使用Rxjava2进行自己的逻辑操作
     *
     * @param phone 手机号
     * @param path 文件路径
    public static Observable<Object> getPhoneCode(String phone, String path) {
        return EasyHttp.post("get_code")
                .params("phone", phone)
                .params("file",new File(path),fileProgressCallBack)
                .execute(new CallClazzProxy<ResultModel<Object>, Object>(Object.class) {
                });
    }*/

    /**
     * 添加订阅关系
     *
     * @param disposable
     */
    public static void addDisposable(Disposable disposable) {
        disposList.add(new SubcribeModel(ActivityUtils.currentActivity().getLocalClassName()+System.currentTimeMillis(),disposable));
        ALog.e("添加订阅", disposList.size());
    }

    /**
     * 在onDestroy里执行
     * 取消所有订阅的网络请求
     */
    /**
     * 在onDestroy里执行
     * 取消所有订阅的网络请求
     */
    public static synchronized void disposeRequest(String className) {
        Iterator<SubcribeModel> it = disposList.iterator();
        while(it.hasNext()){
            SubcribeModel subcribeModel=it.next();
            String key=subcribeModel.getClassName();
            if(!StringUtils.isNull(key)&&key.startsWith(className)){
                EasyHttp.cancelSubscription(subcribeModel.getmDisposable());
                subcribeModel.setClassName(null);
                subcribeModel.setmDisposable(null);
                it.remove();
            }
        }
        ALog.e("取消订阅", disposList.size());
    }

}
