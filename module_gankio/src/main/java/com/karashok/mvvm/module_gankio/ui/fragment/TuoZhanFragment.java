package com.karashok.mvvm.module_gankio.ui.fragment;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;

import com.karashok.library.common.base.BaseLazyFragment;
import com.karashok.mvvm.module_gankio.R;
import com.karashok.mvvm.module_gankio.data.view_modle.FgtTuozhanVM;
import com.karashok.mvvm.module_gankio.databinding.FragmentTuozhanBinding;

/**
 * @author KaraShok(张耀中)
 * DESCRIPTION 拓展资源 Fragment
 * @name TuoZhanFragment
 * @date 2019/03/24 20:19
 **/
public class TuoZhanFragment extends BaseLazyFragment<FgtTuozhanVM, FragmentTuozhanBinding> {
    @Override
    protected int createView() {
        return R.layout.fragment_tuozhan;
    }

    @Override
    protected boolean isDataBinding() {
        return true;
    }

    @Override
    protected Class bindViewModel() {
        return FgtTuozhanVM.class;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDataBinding.setFgtTuozhanVM(mViewModel);
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
