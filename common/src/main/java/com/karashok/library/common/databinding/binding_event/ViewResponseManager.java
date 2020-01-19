package com.karashok.library.common.databinding.binding_event;

/**
 * @author KaraShokZ(zhangyaozhong)
 * @name ViewCommandManager
 * DESCRIPTION View绑定事件的带判断回调的管理类
 * @date 2018/06/16/下午6:18
 */
public class ViewResponseManager<T,R> {

    private Fun3<T,R> mFun3;

    public ViewResponseManager(Fun3<T, R> mFun3) {
        this.mFun3 = mFun3;
    }

    public R execute(T params) {
        if (mFun3 != null) {
            return mFun3.action(params);
        }else {
            return null;
        }
    }
}
