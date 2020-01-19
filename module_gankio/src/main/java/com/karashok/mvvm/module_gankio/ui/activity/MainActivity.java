package com.karashok.mvvm.module_gankio.ui.activity;

import android.view.View;

import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.karashok.library.common.base.BaseActivity;
import com.karashok.mvvm.module_gankio.MGankIOConstant;
import com.karashok.mvvm.module_gankio.R;
import com.karashok.mvvm.module_gankio.data.view_modle.AtyMainVM;
import com.karashok.mvvm.module_gankio.databinding.ActivityMainBinding;
import com.karashok.mvvm.module_gankio.ui.fragment.MainAtyClassificationFragment;
import com.karashok.mvvm.module_gankio.ui.fragment.MainAtyMeFragment;
import com.karashok.mvvm.module_gankio.ui.fragment.MainAtyRecommendationFragment;

@Route(path = MGankIOConstant.ROUTER_MODULE_GANKIO_ATY_MAIN)
public class MainActivity extends BaseActivity<AtyMainVM, ActivityMainBinding> {

    private MainAtyClassificationFragment mClassificationFgt = new MainAtyClassificationFragment();
    private MainAtyRecommendationFragment mRecommendationFgt = new MainAtyRecommendationFragment();
    private MainAtyMeFragment mMeFgt = new MainAtyMeFragment();

    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean isDataBinding() {
        return true;
    }

    @Override
    protected Class<AtyMainVM> bindViewModel() {
        return AtyMainVM.class;
    }

    @Override
    protected void create() {
        super.create();
        mDataBinding.setAtyMainVM(mViewModel);
        mViewModel.bindActivity(this);
        mDataBinding.executePendingBindings();

        addFragment(R.id.module_gank_main_fl,mClassificationFgt);
        addFragment(R.id.module_gank_main_fl,mRecommendationFgt);
        addFragment(R.id.module_gank_main_fl,mMeFgt);
        showOrHideFragment(0);

        mDataBinding.moduleGankMainClassificationRb.setOnClickListener(clickListener);
        mDataBinding.moduleGankMainRecommendationRb.setOnClickListener(clickListener);
        mDataBinding.moduleGankMainMeRb.setOnClickListener(clickListener);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int viewId = view.getId();
            int index = 0;
            if (viewId == R.id.module_gank_main_classification_rb){
                index = 0;
            }else if (viewId == R.id.module_gank_main_recommendation_rb){
                index = 1;
            }else if (viewId == R.id.module_gank_main_me_rb){
                index = 2;
            }else {}
            showOrHideFragment(index);
            mViewModel.setRadioButtonCheckedStates(index);
        }
    };

    private void showOrHideFragment(int index){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (index){
            case 0:
                fragmentTransaction.show(mClassificationFgt);
                fragmentTransaction.hide(mRecommendationFgt);
                fragmentTransaction.hide(mMeFgt);
                break;
            case 1:
                fragmentTransaction.show(mRecommendationFgt);
                fragmentTransaction.hide(mClassificationFgt);
                fragmentTransaction.hide(mMeFgt);
                break;
            case 2:
                fragmentTransaction.show(mMeFgt);
                fragmentTransaction.hide(mRecommendationFgt);
                fragmentTransaction.hide(mClassificationFgt);
                break;
            default:
                break;
        }
        fragmentTransaction.commit();
    }
}
