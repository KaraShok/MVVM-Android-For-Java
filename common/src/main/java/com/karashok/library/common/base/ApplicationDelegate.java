package com.karashok.library.common.base;

/**
 * @author KaraShokZ(zhangyaozhong)
 * @name ApplicationDelegate
 * DESCRIPTION 让各个模块都可以接收到application的生命周期
 * @date 2018/06/16/下午6:18
 */
public interface ApplicationDelegate {

    /**
     * 应用创建
     */
    void onCreate();

    /**
     * 应用中止
     */
    void onTerminate();

    /**
     * 低内存
     */
    void onLowMemory();

    /**
     * 清空内存
     * @param level
     */
    void onTrimMemory(int level);
}
