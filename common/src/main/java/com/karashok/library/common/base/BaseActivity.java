package com.karashok.library.common.base;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.CallSuper;
import androidx.annotation.CheckResult;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.ViewModelProviders;

import com.karashok.library.module_util.utilcode.utils.app_util.ActivityManager;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * @author KaraShokZ(zhangyaozhong)
 * @name BaseActivity
 * DESCRIPTION Activity的基类
 * @date 2018/06/16/下午6:18
 */
public abstract class BaseActivity<V extends BaseViewModel, D extends ViewDataBinding> extends AppCompatActivity implements LifecycleProvider<ActivityEvent> {

    /**
     * Rx生命周期Subject实例
     */
    private final BehaviorSubject<ActivityEvent> mRxLifecycleSubject = BehaviorSubject.create();

    /**
     * DataBinding实例
     */
    protected D mDataBinding;

    /**
     * ViewModel实例
     */
    protected V mViewModel;

    private ArrayList<LifecycleObserver> mObserverList;

    /**
     * Rx生命回收
     * @return
     */
    @Override
    @NonNull
    @CheckResult
    public final Observable<ActivityEvent> lifecycle() {
        return mRxLifecycleSubject.hide();
    }

    /**
     * Rx生命消失出发回调
     * @param event
     * @param <T>
     * @return
     */
    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull ActivityEvent event) {
        return RxLifecycle.bindUntilEvent(mRxLifecycleSubject, event);
    }

    /**
     * 接受Rx绑定的生命回调
     * @param <T>
     * @return
     */
    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycleAndroid.bindActivity(mRxLifecycleSubject);
    }

    /**
     * 绑定生命周期监听
     * @param observer
     */
    protected final void bindLifeObserver(LifecycleObserver observer) {
        if (mObserverList == null) {
            mObserverList = new ArrayList<>();
        }
        mObserverList.add(observer);
        getLifecycle().addObserver(observer);
    }

    /**
     * 解绑生命周期监听
     */
    protected final void unbindLifeObserver() {
        for (LifecycleObserver observer : mObserverList) {
            getLifecycle().removeObserver(observer);
        }
    }

    /**
     * 填充布局
     * @return
     */
    protected abstract @LayoutRes
    int layoutId();

    /**
     * 是否是DataBinding
     * @return
     */
    protected abstract boolean isDataBinding();

    /**
     * 绑定ViewModel
     * @return
     */
    protected abstract Class<V> bindViewModel();

    /**
     * 创建时走这个方法
     */
    protected void create(){

    }

    @Override
    @CallSuper
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRxLifecycleSubject.onNext(ActivityEvent.CREATE);
        ActivityManager.getActivityManagerInstance().pushActivity(this);

        // 生成ViewModle
        if (bindViewModel() != null){
            mViewModel = ViewModelProviders.of(this).get(bindViewModel());
        }

        // 绑定生命周期
        if (mViewModel != null){
            bindLifeObserver(mViewModel);
        }

        // 填充布局
        if (layoutId() > 0){
            if (isDataBinding()){
                mDataBinding = DataBindingUtil.setContentView(this, layoutId());
            }else {
                setContentView(layoutId());
            }
        }
        create();
    }

    @Override
    @CallSuper
    protected void onStart() {
        super.onStart();
        mRxLifecycleSubject.onNext(ActivityEvent.START);
    }

    @Override
    @CallSuper
    protected void onResume() {
        super.onResume();
        mRxLifecycleSubject.onNext(ActivityEvent.RESUME);
    }

    @Override
    @CallSuper
    protected void onPause() {
        super.onPause();
        mRxLifecycleSubject.onNext(ActivityEvent.PAUSE);
    }

    @Override
    @CallSuper
    protected void onStop() {
        super.onStop();
        mRxLifecycleSubject.onNext(ActivityEvent.STOP);
    }

    @Override
    @CallSuper
    protected void onDestroy() {
        mRxLifecycleSubject.onNext(ActivityEvent.DESTROY);
        ActivityManager.getActivityManagerInstance().destoryActivity(this);
        super.onDestroy();
        if (isDataBinding() && mDataBinding != null){
            mDataBinding.unbind();
            mDataBinding = null;
        }
        if (mObserverList != null) {
            unbindLifeObserver();
            mViewModel.onCleared();
            mViewModel = null;
            mObserverList.clear();
            mObserverList = null;
        }
    }

    /**
     * 添加Fragment
     * @param containerViewId
     * @param fragment
     */
    protected final void addFragment(@IdRes int containerViewId, Fragment fragment) {
        FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
        transaction.add(containerViewId, fragment);
        transaction.commit();
    }

    /**
     * 创建Activity
     * @param openClass
     * @param bundle
     */
    public final void startActivity(Class openClass, Bundle bundle){
        Intent intent = new Intent(this, openClass);
        if (bundle != null){
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 创建Activity For Result
     * @param openClass
     * @param bundle
     * @param requestCode
     */
    public final void startActivityForResult(Class openClass, Bundle bundle, int requestCode){
        Intent intent = new Intent(this, openClass);
        if (bundle != null){
            intent.putExtras(bundle);
        }
        startActivityForResult(intent,requestCode);
    }

    /**
     * 返回数据
     * @param bundle
     * @param resultCode
     */
    public final void setResult(Bundle bundle, int resultCode){
        Intent intent = new Intent();
        if (bundle != null){
            intent.putExtras(bundle);
        }
        setResult(resultCode,intent);
        finish();
    }

}
