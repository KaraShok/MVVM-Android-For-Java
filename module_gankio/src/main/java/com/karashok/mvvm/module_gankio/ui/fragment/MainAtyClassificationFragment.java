package com.karashok.mvvm.module_gankio.ui.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.karashok.library.common.base.BaseFragment;
import com.karashok.mvvm.module_gankio.R;
import com.karashok.mvvm.module_gankio.data.view_modle.FgtMainAtyClassificationVM;
import com.karashok.mvvm.module_gankio.databinding.FragmentMainAtyClassificationBinding;

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION 分类
 * @name MainAtyClassificationFragment
 * @date 2019/03/27 19:54
 **/
public class MainAtyClassificationFragment extends BaseFragment<FgtMainAtyClassificationVM, FragmentMainAtyClassificationBinding> {
    @Override
    protected int createView() {
        return R.layout.fragment_main_aty_classification;
    }

    @Override
    protected boolean isDataBinding() {
        return true;
    }

    @Override
    protected Class<FgtMainAtyClassificationVM> bindViewModel() {
        return FgtMainAtyClassificationVM.class;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDataBinding.setFgtMainAtyClassificationVM(mViewModel);
        mViewModel.bindFragment(this);
        mDataBinding.executePendingBindings();

        mDataBinding.moduleGankClassificationTl.setupWithViewPager(mDataBinding.moduleGankClassificationVp);
    }

}
