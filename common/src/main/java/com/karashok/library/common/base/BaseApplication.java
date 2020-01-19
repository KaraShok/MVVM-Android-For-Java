package com.karashok.library.common.base;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.apkfuns.logutils.LogUtils;
import com.karashok.library.module_util.utilcode.utils.Utils;
import com.karashok.library.module_util.utilcode.utils.app_util.AppUtils;
import com.karashok.library.module_util.utilcode.utils.base_util.ClassUtils;

import java.util.List;

/**
 * @author KaraShokZ(zhangyaozhong)
 * @name BaseApplication
 * DESCRIPTION
 * 要想使用BaseApplication，必须在组件中实现自己的Application，并且继承BaseApplication；
 * 组件中实现的Application必须在debug包中的AndroidManifest.xml中注册，否则无法使用；
 * 组件的Application需置于java/debug文件夹中，不得放于主代码；
 * 组件中获取Context的方法必须为:AppUtils.getContext()，不允许其他写法；
 * @date 2018/06/16/下午6:18
 */
public class BaseApplication extends Application {
    public static final String ROOT_PACKAGE = "com.icourt.icontract";

    private List<ApplicationDelegate> mAppDelegateList;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppDelegateList = ClassUtils.getObjectsWithInterface(this, ApplicationDelegate.class, ROOT_PACKAGE);
        for (ApplicationDelegate delegate : mAppDelegateList) {
            delegate.onCreate();
        }
        init();

    }

    private void init(){
        initUtils();
        initRouter();
    }

    private void initUtils(){
        Utils.init(this);
        LogUtils.getLogConfig()
                .configAllowLog(true)
                .configTagPrefix("danxx")
                .configShowBorders(true)
                .configFormatTag("%d{HH:mm:ss:SSS} %t %c{-5}");
    }

    private void initRouter(){
        if (AppUtils.isAppDebug()) {
            //开启InstantRun之后，一定要在ARouter.init之前调用openDebug
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        for (ApplicationDelegate delegate : mAppDelegateList) {
            delegate.onTerminate();
        }
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
        for (ApplicationDelegate delegate : mAppDelegateList) {
            delegate.onLowMemory();
        }
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        for (ApplicationDelegate delegate : mAppDelegateList) {
            delegate.onTrimMemory(level);
        }
    }
}
