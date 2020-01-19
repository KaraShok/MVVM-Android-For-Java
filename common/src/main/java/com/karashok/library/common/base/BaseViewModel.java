package com.karashok.library.common.base;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.karashok.library.common.net.DataCallBack;

/**
 * @author KaraShokZ(zhangyaozhong)
 * @name BaseViewModel
 * DESCRIPTION ViewModel基类
 * @date 2018/06/16/下午6:18
 */
public abstract class BaseViewModel<T,R extends DataManager> extends AndroidViewModel implements DataCallBack<T>, LifecycleObserver {

    protected BaseActivity mActivity;
    protected BaseFragment mFragment;

    /**
     * 用于获取数据的对象
     */
    protected R mDataManager;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        if (initDataManager() != null){
            mDataManager = initDataManager();
        }
    }

    /**
     * 初始化DataManager
     * @return
     */
    protected abstract R initDataManager();

    /**
     * 绑定Activity
     * @param activity
     */
    public void bindActivity(@NonNull BaseActivity activity){
        mActivity = activity;
        if (mDataManager != null){
            mDataManager.bindActivity(activity);
        }
    }

    /**
     * 绑定Fragment
     * @param fragment
     */
    public void bindFragment(@NonNull BaseFragment fragment){
        mFragment = fragment;
        if (mDataManager != null){
            mDataManager.bindFragment(fragment);
        }
    }

    /**
     * 生命周期创建回调
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void lifeCreate(){

    }

    /**
     * 生命周期开始回调
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void lifeStart(){

    }

    /**
     * 生命周期获焦回调
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void lifeResume(){

    }

    /**
     * 生命周期失焦回调
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void lifePause(){

    }

    /**
     * 生命周期停止回调
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void lifeStop(){

    }

    /**
     * 生命周期销毁回调
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void lifeDestroy(){
        onCleared();
    }

    /**
     * 清理回调
     */
    @Override
    protected void onCleared() {
        super.onCleared();
        if (mDataManager != null){
            mDataManager.onCleared();
        }
        if (mActivity != null){
            mActivity = null;
        }

    }

    /**
     * 数据开始回调
     */
    @Override
    public void dataStart() {

    }

    /**
     * 数据回调
     */
    @Override
    public void dataSuccess(T data) {

    }

    /**
     * 数据失败回调
     */
    @Override
    public void dataError(Throwable e) {

    }

    /**
     * 数据完成回调
     */
    @Override
    public void dataComplete() {

    }

    /**
     * 数据取消
     */
    @Override
    public void dataRequestCancel() {

    }
}
