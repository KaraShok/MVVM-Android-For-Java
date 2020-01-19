package com.karashok.mvvm.module_gankio.ui.fragment;

import com.karashok.library.common.base.BaseFragment;
import com.karashok.mvvm.module_gankio.R;

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION 我
 * @name MainAtyMeFragment
 * @date 2019/03/27 19:56
 **/
public class MainAtyMeFragment extends BaseFragment {
    @Override
    protected int createView() {
        return R.layout.fragment_main_aty_me;
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
