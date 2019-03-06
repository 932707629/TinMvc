package me.soushin.tinmvc.model;

import com.zhouyou.http.model.ApiResult;

/**
 * 与服务端约定的返回体
 * 根据约定的返回参数更改这个实体类
 * result返回0是成功
 *
 * @auther SouShin
 * @time 2019/1/11 11:09
 */
public class ResultModel<T> extends ApiResult<T> {
    private int result;
    private String message;
    private T reData;

    @Override
    public String getMsg() {
        return this.message;
    }

    @Override
    public void setMsg(String msg) {
        this.message = msg;
    }

    @Override
    public T getData() {
        return this.reData;
    }

    @Override
    public void setData(T data) {
        this.reData = data;
    }

    @Override
    public boolean isOk() {
        // TODO: 2019/1/11 必须设置成功失败
        return result == 0;
    }

    @Override
    public int getCode() {
        return this.result;
    }

    @Override
    public void setCode(int code) {
        this.result = code;
    }

}
