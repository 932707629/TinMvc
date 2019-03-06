package me.soushin.tinmvc.model;

import io.reactivex.disposables.Disposable;

/**
 * 订阅关系
 * @auther SouShin
 * @time 2019/2/25 14:20
 */
public class SubcribeModel {

    public SubcribeModel() {
    }

    public SubcribeModel(String className, Disposable mDisposable) {
        this.className = className;
        this.mDisposable = mDisposable;
    }

    private String className;
    private Disposable mDisposable;


    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Disposable getmDisposable() {
        return mDisposable;
    }

    public void setmDisposable(Disposable mDisposable) {
        this.mDisposable = mDisposable;
    }
}
