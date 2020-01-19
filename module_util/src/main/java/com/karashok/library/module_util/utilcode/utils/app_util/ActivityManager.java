package com.karashok.library.module_util.utilcode.utils.app_util;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Stack;

/**
 * @author Ralf(wanglixin)
 * DESCRIPTION Activity管理类
 * @name ActivityManager
 * @date 2018/06/01 下午8:54
 **/
public final class ActivityManager {

    private static final Stack<AppCompatActivity> mActivityStack = new Stack<>();
    private static ActivityManager mActivityManager;

    private ActivityManager() {

    }

    /**
     * 获取Activity任务栈的数量
     * @return
     */
    public int getAtyStackSize(){
        return mActivityStack.size();
    }

    /**
     * 根据下标获取Activity
     * @return
     */
    public AppCompatActivity getAtyByIndex(int index){
        return mActivityStack.get(index);
    }

    /**
     * 删除特定下标的Activity
     * @return
     */
    public void destoryAtyByIndex(int index){
        mActivityStack.get(index).finish();
        mActivityStack.remove(index);
    }

    /**
     * 获得ActivityManager对象
     * @return
     */
    public static ActivityManager getActivityManagerInstance(){
        if (mActivityManager == null){
            synchronized (ActivityManager.class){
                if (mActivityManager == null){
                    mActivityManager = new ActivityManager();
                }
            }
        }
        return mActivityManager;
    }

    /**
     * 将特定的Activity推出栈
     * @param activity
     */
    public void popActivity(@NonNull AppCompatActivity activity){
        if (mActivityStack.isEmpty()){
            return;
        }else {
            if (mActivityStack.contains(activity)){
                mActivityStack.remove(activity);
            }
        }
    }

    /**
     * 将栈中除了特定的Activity，将其他的Activity推出栈
     * @param cls
     */
    public void popAllActivityExceptOne(@NonNull Class cls){
        if (mActivityStack.isEmpty()){
            return;
        }else {
            int size = mActivityStack.size();
            for (int i = size - 1; i > 0; i--){
                AppCompatActivity activity = mActivityStack.get(i);

                if (activity == null) {
                    continue;
                }

                if (activity.getClass().equals(cls)){
                    continue;
                }
                destoryActivity(activity);
            }
        }
    }

    /**
     * 将栈中的所有Activity推出
     */
    public void popAllActivity(){
        popAllActivityExceptOne(ActivityManager.class);
    }

    /**
     * 将Activity压如栈中
     * @param activity
     */
    public void pushActivity(AppCompatActivity activity){
        // TODO: 2018/2/8 应该对栈的大小进行限制，防止堆栈溢出。推荐30个
        mActivityStack.add(activity);
    }

    /**
     * 销毁特定的Activity
     * @param activity
     */
    public void destoryActivity(@NonNull AppCompatActivity activity){
        if (mActivityStack.isEmpty()){
            return;
        }else {
            if (mActivityStack.contains(activity)){
                activity.finish();
                mActivityStack.remove(activity);
            }
        }
    }

    /**
     * 获取当前的Activity
     * @return
     */
    public AppCompatActivity getTopActivity(){
        if (mActivityStack.isEmpty()){
            return null;
        }else {
            return mActivityStack.lastElement();
        }
    }

    /**
     * View获取Activity的工具
     *
     * @param view view
     * @return Activity
     */
    public static @NonNull AppCompatActivity getActivityFromView(View view) {
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof AppCompatActivity) {
                return (AppCompatActivity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }

        throw new IllegalStateException("View " + view + " is not attached to an Activity");
    }

}
