package com.karashok.mvvm.module_gankio.ui.fragment;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;

import com.karashok.library.common.base.BaseLazyFragment;
import com.karashok.mvvm.module_gankio.R;
import com.karashok.mvvm.module_gankio.data.view_modle.FgtAndroidVM;
import com.karashok.mvvm.module_gankio.databinding.FragmentAndroidBinding;

/**
 * @author KaraShok(张耀中)
 * DESCRIPTION Android Fragment
 * @name AndroidFragment
 * @date 2019/03/24 20:18
 **/
public class AndroidFragment extends BaseLazyFragment<FgtAndroidVM, FragmentAndroidBinding> {
    @Override
    protected int createView() {
        return R.layout.fragment_android;
    }

    @Override
    protected boolean isDataBinding() {
        return true;
    }

    @Override
    protected Class bindViewModel() {
        return FgtAndroidVM.class;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDataBinding.setFgtAndroidVM(mViewModel);
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
