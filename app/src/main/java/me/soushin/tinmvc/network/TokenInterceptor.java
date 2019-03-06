package me.soushin.tinmvc.network;

import android.text.TextUtils;

import com.blankj.ALog;
import me.soushin.tinmvc.model.ResultModel;
import me.soushin.tinmvc.utils.ActivityUtils;
import me.soushin.tinmvc.utils.GsonUtils;
import com.zhouyou.http.interceptor.BaseExpiredInterceptor;
import com.zhouyou.http.utils.HttpLog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 处理token过期等问题
 *
 * @author SouShin
 * @time 2018/12/10 13:32
 */
public class TokenInterceptor extends BaseExpiredInterceptor {
    private ResultModel resultModel;

    @Override
    public boolean isResponseExpired(Response response, String bodyString) {
        resultModel = GsonUtils.toBean(bodyString, ResultModel.class);
        /*if (resultModel != null) {
            ALog.e("token拦截isResponseExpired", bodyString);
            return resultModel.getData() != null;
        }*/
        return false;
    }

    /**
     * 只有上面配置的过期情况才会执行这里(return true;)
     * token 过期处理方案
     * 1 跳转登录页
     * 2 自动请求登录接口刷新token 并继续该接口的业务
     * @param chain
     * @param bodyString
     * @return
     */
    @Override
    public Response responseExpired(Chain chain, String bodyString) {
        ALog.e("token拦截responseExpired", bodyString);
        HttpClient.disposeRequest(ActivityUtils.currentActivity().getLocalClassName());
//        第一种处理方式:直接跳转登录页
//        App.goLogin();
        if (resultModel != null) {
            try {
                return chain.proceed(chain.request());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        第二种处理方式: 自动请求登录接口刷新token 并继续该接口的业务
        /*try {
            refreshToken();
            if (resultModel != null) {
                return processAccessTokenError(chain, chain.request());
            }
        }catch (Exception e){

        }*/
        return null;
    }

    /**
     * 执行登录请求刷新本地token
     * 请求成功后一定要把token保存下来
     * @throws IOException
     */
    public void refreshToken() throws IOException {

    }

    /**
     * 重新拼装并执行Request
     */
    private Response processAccessTokenError(Chain chain, Request request) throws IOException {
        // create a new request and modify it accordingly using the new token
        String method = request.method();
        FormBody oldBody = (FormBody) request.body();
        if (oldBody == null) {
            if (method.equalsIgnoreCase("GET")) {
                oldBody = getRequestParams(request.url().query());
            } else {
                return chain.proceed(request);
            }
        }
        FormBody.Builder newBody = new FormBody.Builder();
        for (int i = 0; i < oldBody.size(); i++) {
            String name = oldBody.encodedName(i);
            String value = oldBody.encodedValue(i);
            newBody.add(name, value);
        }

        Request newRequest;
        if (method.equalsIgnoreCase("GET")) {
            String url = packageParams(newBody.build());
            HttpLog.i("uuok.GET.Error.newUrl:" + url);
            HttpUrl newHrrpIrl = request.url().newBuilder().query(url).build();
            newRequest = request.newBuilder().url(newHrrpIrl).get().build();
        } else {
            newRequest = request.newBuilder().post(newBody.build()).build();
        }
        return chain.proceed(newRequest);
    }

    /**
     * 将GET请求的参数封装成FormBody
     */
    private FormBody getRequestParams(String params) {
        if (params == null)
            return null;
        String[] strArr = params.split("&");
        if (strArr == null) {
            return null;
        }
        TreeMap<String, String> map = new TreeMap<>();
        FormBody.Builder fBulder = new FormBody.Builder();
        for (String s : strArr) {
            String[] sArr = s.split("=");
            if (sArr.length < 2)
                continue;
            map.put(sArr[0], sArr[1]);
            fBulder.add(sArr[0], sArr[1]);
        }
        FormBody formBody = fBulder.build();
        return formBody;
    }

    /**
     * 封装参数
     */
    private String packageParams(FormBody oldBody) {
        List<String> namesAndValues = new ArrayList<>();
        for (int i = 0; i < oldBody.size(); i++) {
            String name = oldBody.encodedName(i);
            String value = oldBody.encodedValue(i);
            if (!TextUtils.isEmpty(name)) {
                namesAndValues.add(name);
                namesAndValues.add(value);
            }
        }
        StringBuilder sb = new StringBuilder();
        namesAndValuesToQueryString(sb, namesAndValues);
        return sb.toString();
    }

    /**
     * 合并GET参数
     */
    private void namesAndValuesToQueryString(StringBuilder out, List<String> namesAndValues) {
        for (int i = 0, size = namesAndValues.size(); i < size; i += 2) {
            String name = namesAndValues.get(i);
            String value = namesAndValues.get(i + 1);
            if (i > 0) out.append('&');
            out.append(name);
            if (value != null) {
                out.append('=');
                out.append(value);
            }
        }
    }

}
