package com.karashok.mvvm.module_gankio.net;

import com.karashok.mvvm.module_gankio.data.entity.DataEntity;
import com.karashok.mvvm.module_gankio.data.entity.ResultEntity;
import com.karashok.mvvm.module_gankio.data.entity.ZhihuEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author KaraShok(zhangyaozhong)
 * @name GankService
 * DESCRIPTION
 * @date 2018/06/26/下午8:32
 */

public interface GankService {

    @GET("/api/4/news/latest")
    Observable<ZhihuEntity> getTopNewsList();

    /**
     *
     * @param category all | Android | iOS | 休息视频 | 福利 | 拓展资源 | 前端 | 瞎推荐 | App
     * @param page
     * @return
     */
    @GET("/api/data/{category}/20/{page}")
    Observable<ResultEntity<DataEntity>> getGankIOList(@Path("category") String category, @Path("page") int page);
}
