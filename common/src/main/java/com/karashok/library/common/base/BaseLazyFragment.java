package com.karashok.library.common.base;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;

/**
 * @author KaraShokZ(zhangyaozhong)
 * @name BaseLazyFragment
 * DESCRIPTION 懒加载的Fragment
 * @date 2018/06/16/下午6:18
 */
public abstract class BaseLazyFragment<V extends BaseViewModel, D extends ViewDataBinding> extends BaseFragment<V,D> {

    /**
     * 当前Fragment是否可见
     */
    private boolean isVisible = false;

    /**
     * 是否与View建立起映射关系
     */
    private boolean isInitView = false;

    /**
     * 是否是第一次加载
     */
    private boolean isFirstLoad = true;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isInitView = true;
        Log.d("BaseLazyFragment", "onActivityCreated: ");
        if (isFirstLoad){
            lazyLoadData();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            isVisible = true;
            Log.d("BaseLazyFragment", "setUserVisibleHint: ");
            lazyLoadData();
        } else {
            isVisible = false;
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    private void lazyLoadData() {
        if (isFirstLoad) {
            Log.d("BaseLazyFragment","第一次加载 " + " isInitView = " + isInitView + "  isVisible = " + isVisible + "   " + "BaseLazyFragment");
        } else {
            Log.d("BaseLazyFragment","不是第一次加载" + " isInitView = " + isInitView + "  isVisible = " + isVisible + "   " + "BaseLazyFragment");
            contentViewShow();
        }
        if (!isFirstLoad || !isVisible || !isInitView) {
            Log.d("BaseLazyFragment","不加载" + "   " + "BaseLazyFragment");
            return;
        }
        Log.d("BaseLazyFragment","完成数据第一次加载");
        contentViewInit();
        contentViewShow();
        isFirstLoad = false;
    }

    /**
     * 页面View初始化
     */
    protected abstract void contentViewInit();
    /**
     * 页面View显示
     */
    protected abstract void contentViewShow();
}
