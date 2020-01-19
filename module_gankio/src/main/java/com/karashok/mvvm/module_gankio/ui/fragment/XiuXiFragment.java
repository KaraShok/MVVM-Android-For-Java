package com.karashok.mvvm.module_gankio.ui.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.karashok.library.common.base.BaseLazyFragment;
import com.karashok.mvvm.module_gankio.R;
import com.karashok.mvvm.module_gankio.data.view_modle.FgtXiuxiVM;
import com.karashok.mvvm.module_gankio.databinding.FragmentXiuxiBinding;

/**
 * @author KaraShok(张耀中)
 * DESCRIPTION 休息视频 Fragment
 * @name XiuXiFragment
 * @date 2019/03/24 20:18
 **/
public class XiuXiFragment extends BaseLazyFragment<FgtXiuxiVM, FragmentXiuxiBinding> {
    @Override
    protected int createView() {
        return R.layout.fragment_xiuxi;
    }

    @Override
    protected boolean isDataBinding() {
        return true;
    }

    @Override
    protected Class bindViewModel() {
        return FgtXiuxiVM.class;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDataBinding.setFgtXiuxiVM(mViewModel);
    }

    @Override
    protected void contentViewInit() {
        mViewModel.bindFragment(this);
        mDataBinding.executePendingBindings();
        mViewModel.initData();
    }

    @Override
    protected void contentViewShow() {

    }
}
