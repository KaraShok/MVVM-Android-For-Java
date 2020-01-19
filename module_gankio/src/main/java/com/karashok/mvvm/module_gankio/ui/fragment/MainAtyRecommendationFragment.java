package com.karashok.mvvm.module_gankio.ui.fragment;

import com.karashok.library.common.base.BaseFragment;
import com.karashok.mvvm.module_gankio.R;

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION 推荐
 * @name MainAtyRecommendationFragment
 * @date 2019/03/27 19:55
 **/
public class MainAtyRecommendationFragment extends BaseFragment {
    @Override
    protected int createView() {
        return R.layout.fragment_main_aty_recommendation;
    }

    @Override
    protected boolean isDataBinding() {
        return false;
    }

    @Override
    protected Class bindViewModel() {
        return null;
    }
}
