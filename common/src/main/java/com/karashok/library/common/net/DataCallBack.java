package com.karashok.library.common.net;

/**
 * @author KaraShokZ(zhangyaozhong)
 * @name DataCallBack
 * DESCRIPTION 数据回调
 * @date 2018/06/16/下午6:18
 */
public interface DataCallBack<T> {

    void dataStart();

    void dataSuccess(T data);

    void dataError(Throwable e);

    void dataComplete();

    void dataRequestCancel();
}
