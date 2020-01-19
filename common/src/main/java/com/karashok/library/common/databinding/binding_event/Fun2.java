package com.karashok.library.common.databinding.binding_event;


import androidx.annotation.IdRes;

/**
 * @author KaraShokZ(zhangyaozhong)
 * @name Fun2
 * DESCRIPTION View绑定事件的带ViewID和数据回调
 * @date 2018/06/16/下午6:18
 */
public interface Fun2<T> {
    void action(@IdRes int viewId, T item);
}
