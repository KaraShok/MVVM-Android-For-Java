package com.karashok.mvvm.module_gankio.ui.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.karashok.library.common.base.BaseLazyFragment;
import com.karashok.mvvm.module_gankio.R;
import com.karashok.mvvm.module_gankio.data.view_modle.FgtQianduanVM;
import com.karashok.mvvm.module_gankio.databinding.FragmentQianduanBinding;

/**
 * @author KaraShok(张耀中)
 * DESCRIPTION 前端 Fragment
 * @name QianDuanFragment
 * @date 2019/03/24 20:19
 **/
public class QianDuanFragment extends BaseLazyFragment<FgtQianduanVM, FragmentQianduanBinding> {
    @Override
    protected int createView() {
        return R.layout.fragment_qianduan;
    }

    @Override
    protected boolean isDataBinding() {
        return true;
    }

    @Override
    protected Class bindViewModel() {
        return FgtQianduanVM.class;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDataBinding.setFgtQianduanVM(mViewModel);
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
