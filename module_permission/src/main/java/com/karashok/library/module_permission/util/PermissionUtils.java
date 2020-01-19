package com.karashok.library.module_permission.util;//package com.icourt.karashok.module_permission.util;
//
//import android.annotation.SuppressLint;
//import android.app.Application;
//import android.content.Context;
//import android.content.res.Resources;
//import android.support.annotation.NonNull;
//import android.view.Display;
//import android.view.WindowManager;
//
///**
// * @author karasjoker(zhangyaozhong)
// * @name PermissionUtils
// * DESCRIPTION
// * @date 2018/04/08/下午3:29
// */
//
//public class PermissionUtils {
//
//    @SuppressLint("StaticFieldLeak")
//    private static Application sApplication;
//
//    /**
//     * 初始化工具类
//     *
//     * @param app 应用
//     */
//    public static void init(@NonNull final Application app) {
//        PermissionUtils.sApplication = app;
//    }
//
//    /**
//     * 获取 Application
//     *
//     * @return Application
//     */
//    public static Application getApp() {
//        if (sApplication != null) return sApplication;
//        throw new NullPointerException("u should init first");
//    }
//
//    /**
//     * 获取状态栏高度（单位：px）
//     *
//     * @return 状态栏高度（单位：px）
//     */
//    public static int getStatusBarHeight() {
//        Resources resources = sApplication.getResources();
//        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
//        return resources.getDimensionPixelSize(resourceId);
//    }
//
//    @SuppressWarnings("deprecation")
//    public static int getScreenHeight(Context context) {
//        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
//        return display.getHeight();
//    }
//}
