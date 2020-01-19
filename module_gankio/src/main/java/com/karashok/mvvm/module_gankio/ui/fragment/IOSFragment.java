package com.karashok.mvvm.module_gankio.ui.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.karashok.library.common.base.BaseLazyFragment;
import com.karashok.mvvm.module_gankio.R;
import com.karashok.mvvm.module_gankio.data.view_modle.FgtIOSVM;
import com.karashok.mvvm.module_gankio.databinding.FragmentIosBinding;

/**
 * @author KaraShok(张耀中)
 * DESCRIPTION IOS Fragment
 * @name IOSFragment
 * @date 2019/03/24 20:18
 **/
public class IOSFragment extends BaseLazyFragment<FgtIOSVM, FragmentIosBinding> {
    @Override
    protected int createView() {
        return R.layout.fragment_ios;
    }

    @Override
    protected boolean isDataBinding() {
        return true;
    }

    @Override
    protected Class bindViewModel() {
        return FgtIOSVM.class;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDataBinding.setFgtIOSVM(mViewModel);
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
