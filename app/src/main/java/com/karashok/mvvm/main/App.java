package com.karashok.mvvm.main;

import android.content.Context;

import androidx.multidex.MultiDex;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.karashok.library.common.base.BaseApplication;
import com.karashok.library.module_util.utilcode.utils.Utils;

/**
 * @author KaraShok(zhangyaozhong)
 * @name App
 * DESCRIPTION app的application
 * @date 2018/06/16/下午6:18
 */

public class App extends BaseApplication {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        Utils.init(this);
    }
}
