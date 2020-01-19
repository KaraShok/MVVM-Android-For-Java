package com.karashok.library.common.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.CheckResult;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.ViewModelProviders;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;

import java.lang.reflect.Field;
import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * @author KaraShokZ(zhangyaozhong)
 * @name BaseFragment
 * DESCRIPTION Fragment的基类
 * @date 2018/06/16/下午6:18
 */
public abstract class BaseFragment<V extends BaseViewModel, D extends ViewDataBinding> extends Fragment implements LifecycleProvider<FragmentEvent> {

    /**
     * Rx生命周期Subject实例
     */
    private final BehaviorSubject<FragmentEvent> mRxLifecycleSubject = BehaviorSubject.create();

    /**
     * DataBinding实例
     */
    protected D mDataBinding;

    /**
     * ViewModel实例
     */
    protected V mViewModel;

    /**
     * 根视图
     */
    protected View mContentView;
    private ArrayList<LifecycleObserver> mObserverList;

    /**
     * Rx生命回收
     * @return
     */
    @Override
    @NonNull
    @CheckResult
    public final Observable<FragmentEvent> lifecycle() {
        return mRxLifecycleSubject.hide();
    }

    /**
     * Rx生命消失出发回调
     * @param event
     * @return
     */
    @Override
    @NonNull
    public final LifecycleTransformer bindUntilEvent(@NonNull FragmentEvent event) {
        return RxLifecycle.bindUntilEvent(mRxLifecycleSubject, event);
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
     * 接受Rx绑定的生命回调
     * @param <T>
     * @return
     */
    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycleAndroid.bindFragment(mRxLifecycleSubject);
    }

    /**
     * 填充布局
     * @return
     */
    protected abstract @LayoutRes
    int createView();

    /**
     * 启用DataBinding时，走这个方法
     */
    protected abstract boolean isDataBinding();

    /**
     * 绑定ViewModel
     * @return
     */
    protected abstract Class<V> bindViewModel();

    /**
     * 创建时，走这个方法
     */
    protected void viewCreated(){

    }

    /**
     * 获取根视图
     * @return
     */
    public View getContentView() {
        return mContentView;
    }

    /**
     * 获取布局中的控件
     * @param viewId
     * @param <T>
     * @return
     */
    @SuppressWarnings("TypeParameterUnusedInFormals")
    public <T extends View> T findViewById(@IdRes int viewId){
        return mContentView != null ? mContentView.findViewById(viewId) : null;
    }

    @Override
    public void onAttach(android.app.Activity activity) {
        super.onAttach(activity);
        mRxLifecycleSubject.onNext(FragmentEvent.ATTACH);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRxLifecycleSubject.onNext(FragmentEvent.CREATE);
        // 生成ViewModle
        if (bindViewModel() != null){
            mViewModel = ViewModelProviders.of(this).get(bindViewModel());
        }

        // 绑定生命周期
        if (mViewModel != null){
            bindLifeObserver(mViewModel);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (createView() > 0){
            if (isDataBinding()){
                mDataBinding = DataBindingUtil.inflate(inflater, createView(), container, false);
                mContentView = mDataBinding.getRoot();
                return mContentView;
            }else {
                mContentView = inflater.inflate(createView(), container, false);
                return mContentView;
            }
        }else {
            return null;
        }

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRxLifecycleSubject.onNext(FragmentEvent.CREATE_VIEW);
        viewCreated();
    }

    @Override
    public void onStart() {
        super.onStart();
        mRxLifecycleSubject.onNext(FragmentEvent.START);
    }

    @Override
    public void onResume() {
        super.onResume();
        mRxLifecycleSubject.onNext(FragmentEvent.RESUME);
    }

    @Override
    public void onPause() {
        super.onPause();
        mRxLifecycleSubject.onNext(FragmentEvent.PAUSE);
    }

    @Override
    public void onStop() {
        super.onStop();
        mRxLifecycleSubject.onNext(FragmentEvent.STOP);
    }

    @Override
    public void onDestroyView() {
        mRxLifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW);
        super.onDestroyView();
        if (mDataBinding != null){
            mDataBinding.unbind();
            mDataBinding = null;
        }
        if (mContentView != null){
            mContentView = null;
        }
    }

    @Override
    public void onDestroy() {
        mRxLifecycleSubject.onNext(FragmentEvent.DESTROY);
        super.onDestroy();
        if (mObserverList != null) {
            unbindLifeObserver();
            mViewModel.onCleared();
            mViewModel = null;
            mObserverList.clear();
            mObserverList = null;
        }
    }

    @Override
    public void onDetach() {
        mRxLifecycleSubject.onNext(FragmentEvent.DETACH);
        super.onDetach();

        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, (Object)null);
        } catch (NoSuchFieldException var2) {
            throw new RuntimeException(var2);
        } catch (IllegalAccessException var3) {
            throw new RuntimeException(var3);
        }
    }

    /**
     * 关闭当前宿主Activity
     */
    public final void finishActivity(){
        getActivity().finish();
    }

    /**
     * 创建Activity
     * @param openClass
     * @param bundle
     */
    public final void startActivity(Class openClass, Bundle bundle){
        Intent intent = new Intent(getActivity(), openClass);
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
        Intent intent = new Intent(getActivity(), openClass);
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
        getActivity().setResult(resultCode,intent);
        getActivity().finish();
    }
}
