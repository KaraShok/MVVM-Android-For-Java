package com.karashok.library.common.databinding.binding_event;

import android.view.View;

/**
 * @author KaraShokZ(zhangyaozhong)
 * @name ViewCommandManager
 * DESCRIPTION View绑定事件的管理类
 * @date 2018/06/16/下午6:18
 */
public class ViewCommandManager<T> {

    private Fun mFun;
    private Fun1<T> mFun1;
    private Fun2<T> mFun2;
    private Fun4 mFun4;

    public ViewCommandManager(Fun4 fun4) {
        this.mFun4 = fun4;
    }

    public ViewCommandManager(Fun fun) {
        this.mFun = fun;
    }

    public ViewCommandManager(Fun1<T> fun1) {
        this.mFun1 = fun1;
    }

    public ViewCommandManager(Fun2<T> fun2) {
        this.mFun2 = fun2;
    }

    public void execute() {
        if (mFun != null) {
            mFun.action();
        }
    }

    public void execute(View view){

        if (mFun4 != null){
            mFun4.action(view);
        }
    }
    public void execute(T params) {
        if (mFun1 != null) {
            mFun1.action(params);
        }
    }

    public void execute(int viewId, T params) {
        if (mFun2 != null) {
            mFun2.action(viewId, params);
        }
    }
}
