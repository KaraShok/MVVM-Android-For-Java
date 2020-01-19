package com.karashok.mvvm.module_gankio.ui.activity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.karashok.library.common.base.BaseActivity;
import com.karashok.mvvm.module_gankio.MGankIOConstant;
import com.karashok.mvvm.module_gankio.R;
import com.karashok.mvvm.module_gankio.data.view_modle.AtyZhihuVM;
import com.karashok.mvvm.module_gankio.databinding.ActivityZhihuBinding;

@Route(path = MGankIOConstant.ROUTER_MODULE_GANKIO_ATY_ZHIHU)
public class ZhihuActivity extends BaseActivity<AtyZhihuVM, ActivityZhihuBinding> {

    @Override
    protected int layoutId() {
        return R.layout.activity_zhihu;
    }

    @Override
    protected boolean isDataBinding() {
        return true;
    }

    @Override
    protected Class<AtyZhihuVM> bindViewModel() {
        return AtyZhihuVM.class;
    }

    @Override
    protected void create() {
        super.create();
        mDataBinding.setAtyZhihuVM(mViewModel);
        mViewModel.bindActivity(this);
        mViewModel.topNewsList();
        mDataBinding.executePendingBindings();
    }
}
