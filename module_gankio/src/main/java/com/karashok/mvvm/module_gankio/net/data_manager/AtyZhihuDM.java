package com.karashok.mvvm.module_gankio.net.data_manager;

import android.content.Context;

import com.karashok.library.common.base.BaseObserver;
import com.karashok.library.common.base.DataManager;
import com.karashok.library.common.net.DataCallBack;
import com.karashok.mvvm.module_gankio.MGankIOConstant;
import com.karashok.mvvm.module_gankio.data.entity.ZhihuEntity;
import com.karashok.mvvm.module_gankio.net.GankService;

import io.reactivex.Observable;

/**
 * @author KaraShok(zhangyaozhong)
 * @name AtyZhihuDM
 * DESCRIPTION
 * @date 2018/06/27/上午11:51
 */

public class AtyZhihuDM extends DataManager {

    public AtyZhihuDM(DataCallBack dataCallBack) {
        super(dataCallBack);
        initNetWork();
    }

    public void getTopNewsList(){
        GankService gankService = mRetrofitClient.requestNetForData(MGankIOConstant.ZHIHU_HOST, GankService.class);
        Observable<ZhihuEntity> topNewsList = gankService.getTopNewsList();
        initNetProcess(topNewsList)
                .subscribe(new BaseObserver<ZhihuEntity>() {

                    @Override
                    public void onNext(ZhihuEntity value) {
                        super.onNext(value);
                        mDataCallBack.dataSuccess(value);
                    }

                    @Override
                    protected Context windowContext() {
                        return null;
                    }
                });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
