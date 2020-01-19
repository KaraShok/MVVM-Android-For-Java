package com.karashok.library.common.base;

import android.content.Context;

import com.karashok.library.module_util.utilcode.utils.view_util.ProgressDialogUtils;

import io.reactivex.observers.DefaultObserver;

/**
 * @author KaraShokZ(zhangyaozhong)
 * @name BaseObserver
 * DESCRIPTION Observerde基类
 * @date 2018/06/16/下午6:18
 */
public abstract class BaseObserver<T> extends DefaultObserver<T> {

    @Override
    protected void onStart() {
        super.onStart();
        if (windowContext() != null){
            // TODO: 2018/2/7 全局遮照开启
            ProgressDialogUtils.getInstance().showDialog(windowContext());
        }
    }

    @Override
    public void onNext(T value) {

    }

    @Override
    public void onError(Throwable e) {
        // TODO: 2018/2/7 全局遮照消失
        ProgressDialogUtils.hideDialog();
        // TODO: 2018/6/26 对异常进行过滤
    }

    @Override
    public void onComplete() {
        // TODO: 2018/2/7 全局遮照消失
        ProgressDialogUtils.hideDialog();
    }

    public void cancelRequest(){
        this.cancel();
    }

    protected abstract Context windowContext();

}
