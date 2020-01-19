package com.karashok.mvvm.module_gankio.net.data_manager;

import android.content.Context;


import com.karashok.library.common.base.BaseObserver;
import com.karashok.library.common.base.DataManager;
import com.karashok.library.common.net.DataCallBack;
import com.karashok.library.module_util.utilcode.utils.base_util.LogUtils;
import com.karashok.mvvm.module_gankio.MGankIOConstant;
import com.karashok.mvvm.module_gankio.data.entity.DataEntity;
import com.karashok.mvvm.module_gankio.data.entity.ResultEntity;
import com.karashok.mvvm.module_gankio.net.GankService;

import io.reactivex.Observable;

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name GankIODM
 * @date 2019/03/25 20:48
 **/
public class GankIODM extends DataManager {

    /**
     * 父类没有初始化任何数据获取工具，子类必须init，否则将会NullPointException
     *
     * @param dataCallBack
     */
    public GankIODM(DataCallBack dataCallBack) {
        super(dataCallBack);
        initNetWork();
    }

    public void getGankIOList(String category, int page){
        LogUtils.dTag("GankIODM", "category = " + category + "--page = " +page);
        GankService gankService = mRetrofitClient.requestNetForData(MGankIOConstant.GankHost, GankService.class);
        Observable<ResultEntity<DataEntity>> topNewsList = gankService.getGankIOList(category,page);
        initNetProcess(topNewsList)
                .subscribe(new BaseObserver<ResultEntity<DataEntity>>() {

                    @Override
                    protected void onStart() {
                        super.onStart();
                        mDataCallBack.dataStart();
                    }

                    @Override
                    public void onNext(ResultEntity<DataEntity> value) {
                        super.onNext(value);
                        mDataCallBack.dataSuccess(value.getResults());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mDataCallBack.dataError(e);
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        mDataCallBack.dataComplete();
                    }

                    @Override
                    protected Context windowContext() {
                        return null;
                    }
                });
    }
}
