package com.karashok.library.module_db.util;

import java.util.List;

public interface GreenDaoCallBack<T> {

    void onSuccess(List<T> result);

    void onFailed();

    void onNotification(boolean result);
}
