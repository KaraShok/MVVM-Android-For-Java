package com.karashok.mvvm.module_gankio.ui.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.karashok.library.common.base.BaseLazyFragment;
import com.karashok.mvvm.module_gankio.R;
import com.karashok.mvvm.module_gankio.data.view_modle.FgtFuliVM;
import com.karashok.mvvm.module_gankio.databinding.FragmentFuliBinding;

/**
 * @author KaraShok(张耀中)
 * DESCRIPTION 福利 Fragment
 * @name FuLiFragment
 * @date 2019/03/24 20:17
 **/
public class FuLiFragment extends BaseLazyFragment<FgtFuliVM, FragmentFuliBinding> {
    @Override
    protected int createView() {
        return R.layout.fragment_fuli;
    }

    @Override
    protected boolean isDataBinding() {
        return true;
    }

    @Override
    protected Class bindViewModel() {
        return FgtFuliVM.class;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDataBinding.setFgtFuliVM(mViewModel);
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
